package com.digi.fireapp.ui.screens.register

sealed class RegisterEvent {
    data class SetUsername(val username: String) : RegisterEvent()
    data class SetEmail(val email: String) : RegisterEvent()
    data class SetPassword(val password: String) : RegisterEvent()
    data class SetConfirmPassword(val confirmPassword: String) : RegisterEvent()
    data object OnSaveUser : RegisterEvent()
    data object ClearError: RegisterEvent()
}