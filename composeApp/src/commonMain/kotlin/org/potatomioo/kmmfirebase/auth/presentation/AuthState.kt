package org.potatomioo.kmmfirebase.auth.presentation

data class AuthState(
    val isLoading: Boolean = false,
    val isSignedIn: Boolean = false,
    val error: String? = null
)