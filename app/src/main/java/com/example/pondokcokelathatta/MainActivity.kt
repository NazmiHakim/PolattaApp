package com.example.pondokcokelathatta

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.example.pondokcokelathatta.ui.navigation.Screen
import com.example.pondokcokelathatta.ui.screens.PolattaApp
import com.example.pondokcokelathatta.ui.theme.PondokCokelatHattaTheme
import com.example.pondokcokelathatta.ui.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {

    private val TAG = "MainActivityLifecycle"
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate Called")
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            PondokCokelatHattaTheme {
                val currentUser = authViewModel.getCurrentUser()
                val startDestination = if (currentUser == null) {
                    Screen.Login.route
                } else {
                    // Jika sudah login, langsung ke Home.
                    // Logika role-based navigation akan ditangani di dalam PolattaApp
                    // setelah login berhasil.
                    Screen.Home.route
                }
                PolattaApp(startDestination = startDestination)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart Called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume Called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause Called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy Called")
    }
}
