package org.potatomioo.kmmfirebase.auth.domain.model

sealed class AuthResult {
    data class Success(val userId: String) : AuthResult()
    data class Error(val message: String) : AuthResult()
}