package com.example.pondokcokelathatta.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pondokcokelathatta.model.MenuItem
import com.example.pondokcokelathatta.ui.components.MenuCard
import com.example.pondokcokelathatta.ui.components.TopBar
import com.example.pondokcokelathatta.ui.viewmodel.MenuViewModel

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    menuViewModel: MenuViewModel,
    onItemClick: (MenuItem) -> Unit
) {
    val favoriteItems by menuViewModel.favorites.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        TopBar()
        if (favoriteItems.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text("Belum ada menu favorit")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                items(favoriteItems.toList()) { item ->
                    Box(Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                        MenuCard(
                            item = item,
                            quantity = menuViewModel.quantities[item.name] ?: 0,
                            onIncrease = { menuViewModel.increaseQuantity(item) },
                            onDecrease = { menuViewModel.decreaseQuantity(item) },
                            onClick = { onItemClick(item) }
                        )
                    }
                }
            }
        }
    }
}