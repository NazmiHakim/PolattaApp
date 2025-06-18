package com.example.pondokcokelathatta

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.example.pondokcokelathatta.ui.screens.PolattaApp
import com.example.pondokcokelathatta.ui.theme.PondokCokelatHattaTheme

class MainActivity : ComponentActivity() {

    private val TAG = "MainActivityLifecycle"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate Called")
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            PondokCokelatHattaTheme {
                PolattaApp()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Dipanggil saat aktivitas akan terlihat oleh pengguna.
        Log.d(TAG, "onStart Called")
    }

    override fun onResume() {
        super.onResume()
        // Dipanggil saat aktivitas mulai berinteraksi dengan pengguna.
        Log.d(TAG, "onResume Called")
    }

    override fun onPause() {
        super.onPause()
        // Dipanggil saat aktivitas lain diprioritaskan.
        // Tempat yang baik untuk menyimpan data yang belum disimpan.
        Log.d(TAG, "onPause Called")
    }

    override fun onStop() {
        super.onStop()
        // Dipanggil saat aktivitas tidak lagi terlihat oleh pengguna.
        Log.d(TAG, "onStop Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        // Dipanggil sebelum aktivitas dihancurkan.
        // Lakukan pembersihan resource di sini.
        Log.d(TAG, "onDestroy Called")
    }
}