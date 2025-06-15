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
    val searchQuery = remember { mutableStateOf("") }

    // State turunan ini akan otomatis diperbarui saat searchQuery berubah.
    val isSearching = searchQuery.value.isNotEmpty()

    // Gabungkan daftar menu untuk pencarian, item rekomendasi akan diprioritaskan.
    val allMenuItems = (DummyData.recommendations + DummyData.menuItems).distinctBy { it.name }

    // Melakukan filter setiap kali query pencarian berubah.
    val searchResults = remember(searchQuery.value) {
        if (searchQuery.value.isBlank()) {
            emptyList()
        } else {
            val query = searchQuery.value.trim()
            // Cari item yang namanya mengandung query. Rekomendasi akan muncul di atas.
            allMenuItems.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
    }

    Scaffold(
        topBar = {
            Column {
                TopBar()
                SearchBar(
                    query = searchQuery.value,
                    onQueryChange = { searchQuery.value = it }
                )
            }
        },
        bottomBar = { BottomNavBar() }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (isSearching) {
                // Tampilkan hasil pencarian jika pengguna sedang mencari
                if (searchResults.isNotEmpty()) {
                    item { Spacer(Modifier.height(16.dp)) }
                    items(searchResults) { item ->
                        Box(Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                            MenuCard(item, onClick = { onItemClick(item) })
                        }
                    }
                } else {
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillParentMaxSize()
                                .padding(16.dp)
                        ) {
                            Text("Menu tidak ditemukan", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            } else {
                // Tampilkan konten default jika tidak sedang mencari
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