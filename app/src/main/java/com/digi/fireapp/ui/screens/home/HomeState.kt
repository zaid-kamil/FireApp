package com.digi.fireapp.ui.screens.home

import com.digi.fireapp.data.CNote

enum class NoteListState {
    LOADING,
    SUCCESS,
    ERROR
}

data class HomeState(
    val username: String = "",
    val notes: List<CNote> = emptyList(),
    val totalNotes: Int = 0,
    val noteListState: NoteListState = NoteListState.LOADING,
    val error: String = "",
)
