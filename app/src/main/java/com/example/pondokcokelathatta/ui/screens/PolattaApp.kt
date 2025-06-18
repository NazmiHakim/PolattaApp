package com.example.pondokcokelathatta.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pondokcokelathatta.data.model.DummyData
import com.example.pondokcokelathatta.ui.components.BottomNavBar
import com.example.pondokcokelathatta.ui.navigation.Screen
import com.example.pondokcokelathatta.ui.viewmodel.MenuViewModel

@Composable
fun PolattaApp() {
    val navController = rememberNavController()
    val menuViewModel: MenuViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(Screen.Home.route) {
            Scaffold(
                bottomBar = { BottomNavBar(navController = navController) }
            ) { innerPadding ->
                PolattaScreen(
                    modifier = Modifier.padding(innerPadding),
                    menuViewModel = menuViewModel,
                    onItemClick = { menuItem ->
                        navController.navigate(Screen.Detail.createRoute(menuItem.name))
                    }
                )
            }
        }
        composable(Screen.Favorite.route) {
            Scaffold(
                bottomBar = { BottomNavBar(navController = navController) }
            ) { innerPadding ->
                FavoriteScreen(
                    modifier = Modifier.padding(innerPadding),
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