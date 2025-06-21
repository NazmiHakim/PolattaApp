package com.example.pondokcokelathatta.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pondokcokelathatta.data.model.DummyData
import com.example.pondokcokelathatta.model.MenuItem
import com.example.pondokcokelathatta.ui.components.*
import com.example.pondokcokelathatta.ui.viewmodel.MenuViewModel

@Composable
fun PolattaScreen(
    modifier: Modifier = Modifier,
    onItemClick: (MenuItem) -> Unit,
    menuViewModel: MenuViewModel,
    showCheckoutButton: Boolean
) {
    val selectedTab = remember { mutableStateOf("Choco Series") }
    var searchQuery by remember { mutableStateOf("") }
    val allMenuItems = remember { (DummyData.menuItems + DummyData.recommendations).distinct() }

    val filteredMenuItems by remember(searchQuery, allMenuItems) {
        derivedStateOf {
            if (searchQuery.isNotBlank()) {
                allMenuItems.filter {
                    it.name.contains(searchQuery, ignoreCase = true)
                }
            } else {
                null
            }
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = if (showCheckoutButton) 80.dp else 0.dp)
    ) {
        item { TopBar() }
        item { SearchBar(query = searchQuery, onQueryChange = { searchQuery = it }) }

        if (filteredMenuItems != null) {
            item { Spacer(Modifier.height(12.dp)) }
            if (filteredMenuItems!!.isEmpty()) {
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillParentMaxSize()
                            .padding(top = 150.dp)
                    ) {
                        Text("Menu tidak ditemukan")
                    }
                }
            } else {
                // TAMBAHKAN key = { it.name } di sini
                items(items = filteredMenuItems!!, key = { it.name }) { item ->
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
        } else {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                CustomerCard()
            }
            item { RecommendationSection(DummyData.recommendations, onItemClick) }
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Menu",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            item { CategoryTabs(selectedTab) }
            item { Spacer(Modifier.height(12.dp)) }

            menuList(
                menuItems = DummyData.menuItems,
                quantities = menuViewModel.quantities,
                onIncrease = { menuViewModel.increaseQuantity(it) },
                onDecrease = { menuViewModel.decreaseQuantity(it) },
                onItemClick = onItemClick
            )
        }
    }
}