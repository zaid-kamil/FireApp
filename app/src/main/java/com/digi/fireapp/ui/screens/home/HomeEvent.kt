package com.digi.fireapp.ui.screens.home

sealed class HomeEvent {
    data class OnSetNoteTitle(val title: String) : HomeEvent()
    data class OnSetNoteContent(val content: String) : HomeEvent()
}