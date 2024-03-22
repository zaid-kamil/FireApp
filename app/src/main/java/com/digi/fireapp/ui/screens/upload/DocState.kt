package com.digi.fireapp.ui.screens.upload

data class DocState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String = ""
)
