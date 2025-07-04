package com.example.pondokcokelathatta.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pondokcokelathatta.model.MenuItem
import com.example.pondokcokelathatta.ui.components.*
import com.example.pondokcokelathatta.ui.viewmodel.MenuViewModel
import com.example.pondokcokelathatta.ui.viewmodel.UiState

@Composable
fun PolattaScreen(
    modifier: Modifier = Modifier,
    onItemClick: (MenuItem) -> Unit,
    onProfileClick: () -> Unit,
    onChatClick: () -> Unit,
    menuViewModel: MenuViewModel,
    showCheckoutButton: Boolean
) {
    val menuUiState by menuViewModel.menuUiState.collectAsState()
    val recommendationsUiState by menuViewModel.recommendationsUiState.collectAsState()
    // --- PERBAIKAN 1: Ambil state kuantitas dengan collectAsState ---
    val quantities by menuViewModel.quantities.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    val selectedTab = remember { mutableStateOf("Choco Series") }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = if (showCheckoutButton) 80.dp else 0.dp)
    ) {
        item { TopBar(onChatClick = onChatClick) }
        item {
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it }
            )
        }
        item {
            Spacer(modifier = Modifier.height(24.dp))
            CustomerCard(onClick = onProfileClick)
        }

        item {
            when (val state = recommendationsUiState) {
                is UiState.Loading -> {
                    Box(modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is UiState.Success -> RecommendationSection(state.data, onItemClick)
                is UiState.Error -> {
                    // --- PERBAIKAN 2: Panggil fungsi refreshData() yang bersifat public ---
                    ErrorState(message = state.message, onRetry = { menuViewModel.refreshData() })
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Menu",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        when (val state = menuUiState) {
            is UiState.Loading -> {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(vertical = 64.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }
            is UiState.Success -> {
                val allMenuItems = state.data

                val filteredMenuItems = if (searchQuery.isNotBlank()) {
                    allMenuItems.filter {
                        it.name.contains(searchQuery, ignoreCase = true)
                    }
                } else {
                    null
                }

                if (filteredMenuItems != null) {
                    if (filteredMenuItems.isEmpty()) {
                        item { EmptyState(message = "Menu '${searchQuery}' tidak ditemukan.") }
                    } else {
                        items(items = filteredMenuItems, key = { it.name }) { item ->
                            MenuCardContainer(item, menuViewModel, quantities, onItemClick)
                        }
                    }
                } else {
                    item { CategoryTabs(selectedTab) }

                    val menuItemsByCategory = allMenuItems.filter { it.category == selectedTab.value }

                    if (menuItemsByCategory.isEmpty()) {
                        item { EmptyState(message = "Menu untuk kategori ini belum tersedia.") }
                    } else {
                        items(items = menuItemsByCategory, key = { it.name }) { item ->
                            MenuCardContainer(item, menuViewModel, quantities, onItemClick)
                        }
                    }
                }
            }
            is UiState.Error -> {
                item {
                    // --- PERBAIKAN 3: Panggil fungsi refreshData() yang bersifat public ---
                    ErrorState(message = state.message, onRetry = { menuViewModel.refreshData() })
                }
            }
        }
    }
}

@Composable
private fun MenuCardContainer(
    item: MenuItem,
    menuViewModel: MenuViewModel,
    // --- PERBAIKAN 4: Terima map kuantitas sebagai parameter ---
    quantities: Map<String, Int>,
    onItemClick: (MenuItem) -> Unit
) {
    Box(Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
        MenuCard(
            item = item,
            // --- PERBAIKAN 5: Gunakan map kuantitas yang sudah diterima ---
            quantity = quantities[item.name] ?: 0,
            onIncrease = { menuViewModel.increaseQuantity(item) },
            onDecrease = { menuViewModel.decreaseQuantity(item) },
            onClick = { onItemClick(item) }
        )
    }
}

@Composable
fun ErrorState(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Oops!", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = message, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Coba Lagi")
        }
    }
}

@Composable
fun EmptyState(message: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth().padding(64.dp)
    ) {
        Text(message, textAlign = TextAlign.Center)
    }
}