package com.example.pondokcokelathatta.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pondokcokelathatta.data.model.DummyData
import com.example.pondokcokelathatta.ui.components.BottomNavBar
import com.example.pondokcokelathatta.ui.components.CheckoutButton
import com.example.pondokcokelathatta.ui.navigation.Screen
import com.example.pondokcokelathatta.ui.viewmodel.MenuViewModel

@Composable
fun PolattaApp() {
    val navController = rememberNavController()
    val menuViewModel: MenuViewModel = viewModel()

    // Ambil state dari ViewModel untuk mengontrol visibilitas tombol checkout
    val totalQuantity by menuViewModel.totalQuantity.collectAsState()
    val totalPrice by menuViewModel.totalPrice.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(Screen.Home.route) {
            Scaffold(
                bottomBar = { BottomNavBar(navController = navController) }
            ) { innerPadding ->
                // Gunakan Box untuk menumpuk layar dengan tombol checkout
                Box(modifier = Modifier.padding(innerPadding)) {
                    PolattaScreen(
                        menuViewModel = menuViewModel,
                        onItemClick = { menuItem ->
                            navController.navigate(Screen.Detail.createRoute(menuItem.name))
                        }
                    )
                    // Tampilkan tombol checkout jika ada item di keranjang
                    AnimatedVisibility(
                        visible = totalQuantity > 0,
                        enter = slideInVertically(initialOffsetY = { it }),
                        exit = slideOutVertically(targetOffsetY = { it }),
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp)
                    ) {
                        CheckoutButton(
                            itemCount = totalQuantity,
                            totalPrice = totalPrice,
                            onClick = { navController.navigate(Screen.Checkout.route) }
                        )
                    }
                }
            }
        }
        composable(Screen.Favorite.route) {
            Scaffold(
                bottomBar = { BottomNavBar(navController = navController) }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    FavoriteScreen(
                        menuViewModel = menuViewModel,
                        onItemClick = { menuItem ->
                            navController.navigate(Screen.Detail.createRoute(menuItem.name))
                        }
                    )
                    // Tampilkan tombol checkout jika ada item di keranjang
                    AnimatedVisibility(
                        visible = totalQuantity > 0,
                        enter = slideInVertically(initialOffsetY = { it }),
                        exit = slideOutVertically(targetOffsetY = { it }),
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp)
                    ) {
                        CheckoutButton(
                            itemCount = totalQuantity,
                            totalPrice = totalPrice,
                            onClick = { navController.navigate(Screen.Checkout.route) }
                        )
                    }
                }
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
        // Tambahkan halaman Checkout ke NavHost
        composable(Screen.Checkout.route) {
            CheckoutScreen(
                menuViewModel = menuViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}