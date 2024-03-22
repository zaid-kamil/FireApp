package com.digi.fireapp.ui.screens.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    var isLoading: Boolean = false,
    var error: String = "",
    var isLoginSuccess: Boolean = false,
)
