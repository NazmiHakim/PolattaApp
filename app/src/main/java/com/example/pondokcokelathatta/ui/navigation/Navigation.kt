package com.example.pondokcokelathatta.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Detail : Screen("detail/{itemName}") {
        fun createRoute(itemName: String) = "detail/$itemName"
    }
}