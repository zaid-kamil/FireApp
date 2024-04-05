package com.digi.fireapp.ui.screens.upload

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.digi.fireapp.data.CDoc
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DocViewModel(
    private val auth: FirebaseAuth = Firebase.auth,
    private val db: FirebaseFirestore = Firebase.firestore,
    private val store: FirebaseStorage = Firebase.storage,
) : ViewModel() {
    private val _state = MutableStateFlow(DocState())
    val state = _state.asStateFlow()

    fun onEvent(event: DocEvent) {
        when (event) {
            is DocEvent.UploadImage -> uploadToCloud(event.uri)
        }
    }

    private fun uploadToCloud(uri: Uri) {
        _state.update { it.copy(uploadStatus = UploadStatus.UPLOADING) }
        val bucket = store.reference.child(COLL_UPLOADS)
        val filename = uri.lastPathSegment ?: "unknown"
        val mimeType = filename.substringAfterLast(".")
        Log.d("DocViewModel", "Uploading $filename to $bucket")
        bucket.child(filename).putFile(uri)
            .addOnSuccessListener {
                // get download url and put it inside the fireStore database
                it.storage.downloadUrl
                    .addOnSuccessListener { url ->
                        val doc = CDoc(
                            uid = auth.currentUser?.uid ?: "admin",
                            url = url.toString(),
                            name = filename,
                            mimetype = mimeType,
                            timestamp = System.currentTimeMillis()
                        )
                        db.collection(COLL_NOTES).add(doc)
                            .addOnSuccessListener {
                                _state.update { state -> state.copy(uploadStatus = UploadStatus.SUCCESS) }
                            }
                            .addOnFailureListener {
                                _state.update { state ->
                                    state.copy(
                                        uploadStatus = UploadStatus.ERROR,
                                        error = it.message ?: "some error occurred"
                                    )
                                }
                            }
                    }
                    .addOnFailureListener {
                        _state.update { it.copy(uploadStatus = UploadStatus.ERROR) }
                    }
            }
            .addOnFailureListener {
                _state.update { state->
                    state.copy(
                        uploadStatus = UploadStatus.ERROR,
                        error = it.message ?: "some error occurred"
                    )
                }
            }
            .addOnProgressListener {
                val totalSize = it.totalByteCount
                val transferred = it.bytesTransferred
                _state.update {
                    it.copy(
                        uploadStatus = UploadStatus.UPLOADING,
                        progress = (transferred * 100 / totalSize).toInt()
                    )
                }
            }

    }

    companion object {
        const val COLL_NOTES = "notes"
        const val COLL_UPLOADS = "uploads"
    }
}