package com.digi.fireapp.ui.screens.home

import com.digi.fireapp.data.CDoc
import com.digi.fireapp.data.CNote

enum class NoteListState {
    LOADING,
    SUCCESS,
    ERROR
}
enum class DocumentListState {
    LOADING,
    SUCCESS,
    ERROR
}

data class HomeState(
    val totalNotes: Int = 0,
    val totalDocuments: Int = 0,
    val error: String = "",
    val username: String = "",
    val notes: List<CNote> = emptyList(),
    val documentList: List<CDoc> = emptyList(),
    val noteListState: NoteListState = NoteListState.LOADING,
    val docListState: DocumentListState = DocumentListState.LOADING,
    val noteTitle: String = "",
    val noteContent: String = "",
)
