package com.example.pondokcokelathatta.ui.viewmodel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pondokcokelathatta.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AuthRepository(application.applicationContext)
    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState

    sealed class AuthState {
        data class Authenticated(val user: FirebaseUser, val role: String) : AuthState()
        object Unauthenticated : AuthState()
        object Loading : AuthState()
        data class Error(val message: String) : AuthState()
    }

    fun getCurrentUser(): FirebaseUser? {
        return repository.getCurrentUser()
    }

    fun signInWithEmailPassword(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val result = repository.signInWithEmailPassword(email, password)
                val user = result.user!!
                val role = repository.getUserRole(user.uid)
                _authState.value = AuthState.Authenticated(user, role)
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val result = repository.signInWithGoogle(idToken)
                val user = result.user!!
                val role = repository.getUserRole(user.uid)
                _authState.value = AuthState.Authenticated(user, role)
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getGoogleSignInIntent(): Intent {
        // Ganti dengan Web Client ID Anda dari file google-services.json
        val webClientId = "308223894643-g5vli7k3ap6024h0k2ejcssncj2m9mja.apps.googleusercontent.com"
        return repository.getGoogleSignInIntent(webClientId)
    }
}
