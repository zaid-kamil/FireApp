package com.digi.fireapp.ui.screens.notes

import com.digi.fireapp.data.CNote
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NoteViewModel(
    private val auth: FirebaseAuth = Firebase.auth,
    private val db: FirebaseFirestore = Firebase.firestore,
) {
    private fun loadAllNotes() {

    }
    private fun saveNewNote() {
        val note = CNote(uid = "", title = "", content = "", importance = 1)
        db.collection(COLL_NOTES).add(note)
            .addOnSuccessListener {
                // Success
            }
            .addOnFailureListener {
                // Failure
            }
    }

    companion object {
        const val COLL_NOTES = "notes"
        const val COLL_UPLOADS = "uploads"
    }
}
