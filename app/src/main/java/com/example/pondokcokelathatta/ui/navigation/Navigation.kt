package com.example.pondokcokelathatta.ui.navigation

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Admin : Screen("admin")
    data object Home : Screen("home")
    data object Favorite : Screen("favorite")
    data object Checkout : Screen("checkout")
    data object Profile : Screen("profile")
    data object EditProfile : Screen("editProfile")
    data object Status : Screen("status")
    data object ChatList : Screen("chatList")
    data object ChatDetail : Screen("chatDetail")
    data object Detail : Screen("detail/{itemName}") {
        fun createRoute(itemName: String) = "detail/$itemName"
    }
}