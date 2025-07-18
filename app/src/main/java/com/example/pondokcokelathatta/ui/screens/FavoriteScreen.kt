package com.example.pondokcokelathatta.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.pondokcokelathatta.ui.viewmodel.UiState

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    menuViewModel: MenuViewModel,
    onItemClick: (MenuItem) -> Unit,
    showCheckoutButton: Boolean,
    onChatClick: () -> Unit
) {
    val favoritesUiState by menuViewModel.favoritesUiState.collectAsState()
    // --- PERBAIKAN 1: Ambil state kuantitas dengan collectAsState ---
    val quantities by menuViewModel.quantities.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        TopBar(onChatClick = onChatClick)

        when (val state = favoritesUiState) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Success -> {
                val favoriteItems = state.data
                if (favoriteItems.isEmpty()) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        EmptyState(message = "Anda belum memiliki menu favorit. Tambahkan dengan menekan ikon hati pada menu.")
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            top = 12.dp,
                            bottom = if (showCheckoutButton) 80.dp else 12.dp
                        )
                    ) {
                        items(favoriteItems) { item -> // Tidak perlu .toList() lagi
                            Box(Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                                MenuCard(
                                    item = item,
                                    // --- PERBAIKAN 2: Gunakan state kuantitas yang sudah diambil ---
                                    quantity = quantities[item.name] ?: 0,
                                    onIncrease = { menuViewModel.increaseQuantity(item) },
                                    onDecrease = { menuViewModel.decreaseQuantity(item) },
                                    onClick = { onItemClick(item) }
                                )
                            }
                        }
                    }
                }
            }
            is UiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    // --- PERBAIKAN 3: Panggil fungsi refreshData() yang bersifat public ---
                    ErrorState(message = state.message, onRetry = { menuViewModel.refreshData() })
                }
            }
        }
    }
}