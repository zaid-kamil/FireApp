package com.digi.fireapp.ui.screens.notes

data class NoteState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String = ""
)
