package org.potatomioo.kmmfirebase.auth.domain.repository

import org.potatomioo.kmmfirebase.auth.domain.model.AuthResult

interface AuthRepository {
    suspend fun signInWithGoogle(): AuthResult
    suspend fun signOut()
    fun isUserSignedIn(): Boolean
}