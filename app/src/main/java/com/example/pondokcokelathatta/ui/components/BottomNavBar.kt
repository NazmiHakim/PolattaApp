package com.example.pondokcokelathatta.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import com.example.pondokcokelathatta.ui.theme.BrownPrimary
import com.example.pondokcokelathatta.ui.theme.TextSecondary

@Composable
fun BottomNavBar() {
    val selectedIndex = remember { mutableIntStateOf(0) }
    val items = listOf("Home", "Order", "Message", "Profile")
    val icons = listOf(Icons.Default.Home, Icons.Default.ShoppingCart, Icons.Default.Email, Icons.Default.Person)

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        items.forEachIndexed { index, screen ->
            NavigationBarItem(
                icon = { Icon(icons[index], contentDescription = screen) },
                label = { Text(screen) },
                selected = selectedIndex.intValue == index,
                onClick = { selectedIndex.intValue = index },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = BrownPrimary,
                    selectedTextColor = BrownPrimary,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary,
                    indicatorColor = MaterialTheme.colorScheme.background // Warna indicator transparan
                )
            )
        }
    }
}