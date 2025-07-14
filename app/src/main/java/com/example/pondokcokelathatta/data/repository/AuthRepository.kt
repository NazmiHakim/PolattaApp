package com.example.pondokcokelathatta.data.repository

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository(private val context: Context) {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun getCurrentUser() = auth.currentUser

    suspend fun signInWithEmailPassword(email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password).await()

    suspend fun signInWithGoogle(idToken: String) =
        auth.signInWithCredential(GoogleAuthProvider.getCredential(idToken, null)).await()

    suspend fun getUserRole(uid: String): String {
        val document = firestore.collection("users").document(uid).get().await()
        return document.getString("role") ?: "customer"
    }

    fun getGoogleSignInIntent(clientId: String): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(clientId)
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(context, gso)
        return googleSignInClient.signInIntent
    }

    fun signOut() {
        auth.signOut()
    }
}