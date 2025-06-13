package com.example.pondokcokelathatta

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pondokcokelathatta.data.model.DummyData
import com.example.pondokcokelathatta.ui.navigation.Screen
import com.example.pondokcokelathatta.ui.screens.DetailScreen
import com.example.pondokcokelathatta.ui.screens.PolattaScreen
import com.example.pondokcokelathatta.ui.theme.PondokCokelatHattaTheme

class MainActivity : ComponentActivity() {

    private val TAG = "MainActivityLifecycle"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate Called")
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            // 1. Bungkus seluruh navigasi aplikasi dengan tema kustom Anda
            PondokCokelatHattaTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.Home.route) {
                    composable(Screen.Home.route) {
                        PolattaScreen(onItemClick = { menuItem ->
                            navController.navigate(Screen.Detail.createRoute(menuItem.name))
                        })
                    }
                    composable(Screen.Detail.route) { backStackEntry ->
                        val itemName = backStackEntry.arguments?.getString("itemName")
                        // 2. Logika pencarian item diperbaiki agar mencari di daftar rekomendasi dan menu
                        val menuItem = DummyData.recommendations.find { it.name == itemName }
                            ?: DummyData.menuItems.find { it.name == itemName }

                        if (menuItem != null) {
                            DetailScreen(menuItem = menuItem, onBack = { navController.popBackStack() })
                        }
                    }
                }
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