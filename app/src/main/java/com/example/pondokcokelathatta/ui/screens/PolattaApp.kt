package com.example.pondokcokelathatta.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pondokcokelathatta.data.model.DummyData
import com.example.pondokcokelathatta.ui.components.BottomNavBar
import com.example.pondokcokelathatta.ui.navigation.Screen
import com.example.pondokcokelathatta.ui.viewmodel.MenuViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PolattaApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val menuViewModel: MenuViewModel = viewModel()

    Scaffold(
        bottomBar = {
            if (currentRoute == Screen.Home.route || currentRoute == Screen.Favorite.route) {
                BottomNavBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
        ) {
            composable(Screen.Home.route) {
                Box(modifier = Modifier.padding(innerPadding)) {
                    PolattaScreen(
                        menuViewModel = menuViewModel,
                        onItemClick = { menuItem ->
                            navController.navigate(Screen.Detail.createRoute(menuItem.name))
                        }
                    )
                }
            }
            composable(Screen.Favorite.route) {
                Box(modifier = Modifier.padding(innerPadding)) {
                    FavoriteScreen(
                        menuViewModel = menuViewModel,
                        onItemClick = { menuItem ->
                            navController.navigate(Screen.Detail.createRoute(menuItem.name))
                        }
                    )
                }
            }
            composable(Screen.Detail.route) { backStackEntry ->
                val itemName = backStackEntry.arguments?.getString("itemName")
                val menuItem = (DummyData.recommendations + DummyData.menuItems).find { it.name == itemName }

                if (menuItem != null) {
                    DetailScreen(
                        menuItem = menuItem,
                        onBack = { navController.popBackStack() },
                        menuViewModel = menuViewModel
                    )
                }
            }
        }
    }
}