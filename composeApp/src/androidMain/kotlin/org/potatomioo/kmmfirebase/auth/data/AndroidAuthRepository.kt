package org.potatomioo.kmmfirebase.auth.data

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.potatomioo.kmmfirebase.R
import org.potatomioo.kmmfirebase.auth.domain.model.AuthResult
import org.potatomioo.kmmfirebase.auth.domain.repository.AuthRepository

class AndroidAuthRepository(
    private val context: Context
) : AuthRepository {
    private val auth = Firebase.auth
    private val signInClient = GoogleSignIn.getClient(
        context,
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    )

    override suspend fun signInWithGoogle(): AuthResult = withContext(Dispatchers.IO) {
        try {
            val signInIntent = signInClient.signInIntent
            val result = signInIntent.await()
            val account = GoogleSignIn.getSignedInAccountFromIntent(result).await()
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            val authResult = auth.signInWithCredential(credential).await()

            AuthResult.Success(authResult.user?.uid ?: "")
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun signOut() {
        auth.signOut()
        signInClient.signOut()
    }

    override fun isUserSignedIn(): Boolean {
        return auth.currentUser != null
    }
}