package com.example.pondokcokelathatta.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pondokcokelathatta.data.model.DummyData
import com.example.pondokcokelathatta.ui.components.BottomNavBar
import com.example.pondokcokelathatta.ui.components.CheckoutButton
import com.example.pondokcokelathatta.ui.navigation.Screen
import com.example.pondokcokelathatta.ui.viewmodel.MenuViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PolattaApp() {
    val navController = rememberNavController()
    val menuViewModel: MenuViewModel = viewModel()

    val totalQuantity by menuViewModel.totalQuantity.collectAsState()
    val totalPrice by menuViewModel.totalPrice.collectAsState()
    val showCheckoutButton = totalQuantity > 0

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(Screen.Home.route) {
            Scaffold(
                bottomBar = { BottomNavBar(navController = navController) }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    PolattaScreen(
                        menuViewModel = menuViewModel,
                        onItemClick = { menuItem ->
                            navController.navigate(Screen.Detail.createRoute(menuItem.name))
                        },
                        onProfileClick = {
                            navController.navigate(Screen.Profile.route)
                        },
                        onChatClick = {
                            navController.navigate(Screen.ChatList.route)
                        },
                        showCheckoutButton = showCheckoutButton
                    )
                    AnimatedVisibility(
                        visible = showCheckoutButton,
                        enter = slideInVertically(initialOffsetY = { it }),
                        exit = slideOutVertically(targetOffsetY = { it }),
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp)
                    ) {
                        CheckoutButton(
                            itemCount = totalQuantity,
                            totalPrice = totalPrice,
                            onClick = {
                                navController.navigate(Screen.Checkout.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
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
                        },
                        onChatClick = {
                            navController.navigate(Screen.ChatList.route)
                        },
                        showCheckoutButton = showCheckoutButton
                    )
                    AnimatedVisibility(
                        visible = showCheckoutButton,
                        enter = slideInVertically(initialOffsetY = { it }),
                        exit = slideOutVertically(targetOffsetY = { it }),
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp)
                    ) {
                        CheckoutButton(
                            itemCount = totalQuantity,
                            totalPrice = totalPrice,
                            onClick = {
                                navController.navigate(Screen.Checkout.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }

        composable(Screen.ChatList.route) {
            ChatListScreen(
                navController = navController,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.ChatDetail.route) {
            ChatScreen(
                onBack = { navController.popBackStack() }
            )
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
        composable(Screen.Checkout.route) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Payment Summary", fontWeight = FontWeight.Bold) },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                    )
                },
                bottomBar = { BottomNavBar(navController = navController) }
            ) { innerPadding ->
                CheckoutScreen(
                    menuViewModel = menuViewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
        composable(Screen.Status.route) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Activity") },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                    )
                },
                bottomBar = { BottomNavBar(navController = navController) }
            ) { innerPadding ->
                StatusScreenContent(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
        composable(Screen.Profile.route) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("My Profile") },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                    )
                },
                bottomBar = { BottomNavBar(navController = navController) }
            ) { innerPadding ->
                ProfileScreen(
                    navController = navController,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
        composable(Screen.EditProfile.route) {
            EditProfileScreen(navController = navController)
        }
    }
}