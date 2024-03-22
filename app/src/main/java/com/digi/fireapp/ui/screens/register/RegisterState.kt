package com.digi.fireapp.ui.screens.register

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val username: String = "",
    val isLoading: Boolean = false,
    val error: String = ""
)
