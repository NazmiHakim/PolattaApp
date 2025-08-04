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

    // Sealed class untuk merepresentasikan state otentikasi
    sealed class AuthState {
        data class Authenticated(val user: FirebaseUser, val role: String) : AuthState()
        data object Unauthenticated : AuthState()
        data object Loading : AuthState()
        data class Error(val message: String) : AuthState()
    }

    init {
        checkCurrentUser()
    }

    private fun checkCurrentUser() {
        viewModelScope.launch {
            val currentUser = repository.getCurrentUser()
            if (currentUser != null) {
                _authState.value = AuthState.Loading
                try {
                    val role = repository.getUserRole(currentUser.uid)
                    _authState.value = AuthState.Authenticated(currentUser, role)
                } catch (e: Exception) {
                    _authState.value = AuthState.Error(e.message ?: "Gagal mendapatkan role pengguna.")
                    // Logout jika gagal mendapatkan role untuk menghindari state yang tidak konsisten
                    repository.signOut()
                    _authState.value = AuthState.Unauthenticated
                }
            } else {
                _authState.value = AuthState.Unauthenticated
            }
        }
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
                _authState.value = AuthState.Error(e.message ?: "Login gagal. Periksa kembali email dan password Anda.")
            }
        }
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val result = repository.signInWithGoogle(idToken)
                val user = result.user!!
                // Periksa apakah ini pengguna baru, jika ya, buat entri di Firestore
                if (result.additionalUserInfo?.isNewUser == true) {
                    // Di sini Anda bisa menambahkan logika untuk membuat dokumen baru di Firestore
                    // untuk pengguna yang baru mendaftar via Google.
                }
                val role = repository.getUserRole(user.uid)
                _authState.value = AuthState.Authenticated(user, role)
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Login dengan Google gagal.")
            }
        }
    }

    fun getGoogleSignInIntent(): Intent {
        // Ganti dengan Web Client ID Anda dari file google-services.json
        val webClientId = "308223894643-g5vli7k3ap6024h0k2ejcssncj2m9mja.apps.googleusercontent.com" //
        return repository.getGoogleSignInIntent(webClientId)
    }

    fun signOut() {
        repository.signOut()
        _authState.value = AuthState.Unauthenticated
    }
}