package com.example.pondokcokelathatta.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pondokcokelathatta.data.model.DummyData
import com.example.pondokcokelathatta.model.MenuItem
import com.example.pondokcokelathatta.ui.components.*
import com.example.pondokcokelathatta.ui.viewmodel.MenuViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PolattaScreen(
    onItemClick: (MenuItem) -> Unit,
    menuViewModel: MenuViewModel = viewModel() // 1. Inisialisasi ViewModel
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

    Scaffold(
        bottomBar = { BottomNavBar() }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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
                    items(filteredMenuItems!!) { item ->
                        Box(Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                            // 2. Teruskan data dan fungsi dari ViewModel ke MenuCard
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

                // 3. Modifikasi panggilan menuList
                menuList(
                    menuItems = DummyData.menuItems,
                    quantities = menuViewModel.quantities, // Teruskan map kuantitas
                    onIncrease = { menuViewModel.increaseQuantity(it) }, // Teruskan fungsi increase
                    onDecrease = { menuViewModel.decreaseQuantity(it) }, // Teruskan fungsi decrease
                    onItemClick = onItemClick
                )
            }
        }
    }
}