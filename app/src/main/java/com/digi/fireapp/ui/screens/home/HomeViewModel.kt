package com.digi.fireapp.ui.screens.home

import androidx.lifecycle.ViewModel
import com.digi.fireapp.data.CDoc
import com.digi.fireapp.data.CNote
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel(
    private val auth: FirebaseAuth = Firebase.auth,
    private val db: FirebaseFirestore = Firebase.firestore,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        loadAllNotes()
        loadAllUploads()
        _state.update {
            it.copy(username = auth.currentUser?.displayName ?: "Guest")
        }
    }

    fun onEvent(event: HomeEvent) {

    }

    private fun loadAllNotes() {
        db.collection(COLL_NOTES)
            .get()
            .addOnFailureListener { e ->
                _state.update {
                    it.copy(
                        error = e.message ?: "Some error occurred",
                        noteListState = NoteListState.ERROR,
                    )
                }
            }
            .addOnSuccessListener { data ->
                val tempNotes = mutableListOf<CNote>()
                for (doc in data) {
                    val note = doc.toObject(CNote::class.java)
                    tempNotes.add(note)
                }
                _state.update {
                    it.copy(
                        notes = tempNotes,
                        noteListState = NoteListState.SUCCESS,
                        error = "",
                        totalNotes = tempNotes.size,
                    )
                }
            }
    }

    private fun loadAllUploads() {
        db.collection(COLL_UPLOADS)
            .get()
            .addOnFailureListener {
                _state.update { state ->
                    state.copy(
                        error = it.message ?: "Some error occurred",
                        docListState = DocumentListState.ERROR,
                    )
                }
            }
            .addOnSuccessListener { querySnapshot ->
                for (doc in querySnapshot) {
                    val upload = doc.toObject(CDoc::class.java)
                    _state.update { state ->
                        state.copy(documentList = state.documentList + upload)
                    }
                }
                _state.update { state ->
                    state.copy(
                        docListState = DocumentListState.SUCCESS,
                        totalDocuments = state.documentList.size,
                    )
                }
            }
    }


    companion object {
        const val COLL_NOTES = "notes"
        const val COLL_UPLOADS = "uploads"
    }
}