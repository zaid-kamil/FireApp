package com.digi.fireapp.ui.screens.register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegisterViewModel() : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            RegisterEvent.OnNavigateToHome -> TODO()
            RegisterEvent.OnNavigateToLogin -> TODO()
            RegisterEvent.OnSaveUser -> TODO()
            is RegisterEvent.SetConfirmPassword -> TODO()
            is RegisterEvent.SetEmail -> TODO()
            is RegisterEvent.SetError -> TODO()
            is RegisterEvent.SetPassword -> TODO()
            is RegisterEvent.SetUsername -> TODO()
        }
    }

    fun register() {}
}
