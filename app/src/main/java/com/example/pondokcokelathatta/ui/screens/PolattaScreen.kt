package com.example.pondokcokelathatta.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pondokcokelathatta.data.model.DummyData
import com.example.pondokcokelathatta.model.MenuItem
import com.example.pondokcokelathatta.ui.components.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PolattaScreen(onItemClick: (MenuItem) -> Unit) {
    val selectedTab = remember { mutableStateOf("Choco Series") }

    Scaffold(
        bottomBar = { BottomNavBar() }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding
        ) {
            item { TopBar() }
            item { SearchBar() }
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

            // Panggil extension function 'menuList' di sini
            menuList(
                menuItems = DummyData.menuItems,
                onItemClick = onItemClick
            )
        }
    }
}