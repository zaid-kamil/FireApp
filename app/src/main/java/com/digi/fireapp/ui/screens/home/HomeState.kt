package com.digi.fireapp.ui.screens.home

data class HomeState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String = ""
)
