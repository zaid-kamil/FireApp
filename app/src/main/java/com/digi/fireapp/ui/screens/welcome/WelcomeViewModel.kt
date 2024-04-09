package com.digi.fireapp.ui.screens.welcome

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


enum class AuthStatus {
    AUTHENTICATED,
    UNAUTHENTICATED
}


data class WelcomeState(
    val authStatus: AuthStatus = AuthStatus.UNAUTHENTICATED,
)

sealed class WelcomeEvent {}

class WelcomeViewModel(
    private val auth: FirebaseAuth = Firebase.auth,
) : ViewModel() {

    private val _state = MutableStateFlow(WelcomeState())
    val state: StateFlow<WelcomeState> = _state.asStateFlow()

    init {
        checkAuthStatus()
    }

    fun onEvent(event: WelcomeEvent) {

    }

    private fun checkAuthStatus() {
        if (auth.currentUser != null) {
            _state.value = WelcomeState(authStatus = AuthStatus.AUTHENTICATED)
        } else {
            _state.value = WelcomeState(authStatus = AuthStatus.UNAUTHENTICATED)
        }
    }
}