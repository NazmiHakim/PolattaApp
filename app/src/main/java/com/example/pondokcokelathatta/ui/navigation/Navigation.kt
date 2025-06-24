package com.example.pondokcokelathatta.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Favorite : Screen("favorite")
    data object Checkout : Screen("checkout")
    data object Profile : Screen("profile") // Add this line
    data object EditProfile : Screen("editProfile") // Add this line
    data object Detail : Screen("detail/{itemName}") {
        fun createRoute(itemName: String) = "detail/$itemName"
    }
}