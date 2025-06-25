package com.example.pondokcokelathatta.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Favorite : Screen("favorite")
    data object Checkout : Screen("checkout")
    data object Profile : Screen("profile")
    data object EditProfile : Screen("editProfile")
    data object Status : Screen("status")

    // MODIFIKASI: Dua rute baru untuk chat
    data object ChatList : Screen("chatList") // Halaman daftar chat
    data object ChatDetail : Screen("chatDetail") // Halaman chat spesifik

    data object Detail : Screen("detail/{itemName}") {
        fun createRoute(itemName: String) = "detail/$itemName"
    }
}