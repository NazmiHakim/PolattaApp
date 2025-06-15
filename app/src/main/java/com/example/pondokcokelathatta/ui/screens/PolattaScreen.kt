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
import com.example.pondokcokelathatta.data.model.DummyData
import com.example.pondokcokelathatta.model.MenuItem
import com.example.pondokcokelathatta.ui.components.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PolattaScreen(onItemClick: (MenuItem) -> Unit) {
    val selectedTab = remember { mutableStateOf("Choco Series") }
    var searchQuery by remember { mutableStateOf("") }
    // Gabungkan dan buat daftar yang berbeda untuk pencarian yang komprehensif
    val allMenuItems = remember { (DummyData.menuItems + DummyData.recommendations).distinct() }

    // Item yang difilter berfungsi sebagai rekomendasi/hasil pencarian
    val filteredMenuItems by remember(searchQuery, allMenuItems) {
        derivedStateOf {
            if (searchQuery.isNotBlank()) {
                allMenuItems.filter {
                    it.name.contains(searchQuery, ignoreCase = true)
                }
            } else {
                null // Tampilkan tampilan default jika query kosong
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
                // Tampilkan hasil pencarian
                item { Spacer(Modifier.height(12.dp)) }
                if (filteredMenuItems!!.isEmpty()) {
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillParentMaxSize()
                                .padding(top = 150.dp) // Beri sedikit jarak dari atas
                        ) {
                            Text("Menu tidak ditemukan")
                        }
                    }
                } else {
                    items(filteredMenuItems!!) { item ->
                        Box(Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                            MenuCard(item, onClick = { onItemClick(item) })
                        }
                    }
                }
            } else {
                // Tampilkan konten default home screen
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
                    onItemClick = onItemClick
                )
            }
        }
    }
}