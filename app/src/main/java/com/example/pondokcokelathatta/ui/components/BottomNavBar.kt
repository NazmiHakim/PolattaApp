package com.example.pondokcokelathatta.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun BottomNavBar() {
    val selectedIndex = remember { mutableStateOf(0) }

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = selectedIndex.value == 0,
            onClick = { selectedIndex.value = 0 }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Order") },
            label = { Text("Order") },
            selected = selectedIndex.value == 1,
            onClick = { selectedIndex.value = 1 }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Email, contentDescription = "Message") },
            label = { Text("Message") },
            selected = selectedIndex.value == 2,
            onClick = { selectedIndex.value = 2 }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = selectedIndex.value == 3,
            onClick = { selectedIndex.value = 3 }
        )
    }
}
