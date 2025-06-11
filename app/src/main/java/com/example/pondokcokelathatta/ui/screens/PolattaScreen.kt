package com.example.pondokcokelathatta.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pondokcokelathatta.data.model.DummyData
import com.example.pondokcokelathatta.ui.components.BottomNavBar
import com.example.pondokcokelathatta.ui.components.CategoryTabs
import com.example.pondokcokelathatta.ui.components.MenuCard
import com.example.pondokcokelathatta.ui.components.RecommendationSection
import com.example.pondokcokelathatta.ui.components.SearchBar
import com.example.pondokcokelathatta.ui.components.TopBar
import com.example.pondokcokelathatta.ui.theme.PondokCokelatHattaTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PolattaScreen() {
    val selectedTab = remember { mutableStateOf("Choco Series") }

    PondokCokelatHattaTheme {
        Scaffold(
            bottomBar = { BottomNavBar() }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = innerPadding // Terapkan padding dari Scaffold untuk system bars
            ) {
                item { TopBar() }
                item { SearchBar() }
                item { RecommendationSection(DummyData.recommendations) }
                item {
                    Text(
                        text = "Menu",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                    )
                }
                item { CategoryTabs(selectedTab) }
                item { Spacer(Modifier.height(12.dp)) } // Spacer seperti di MenuList asli
                items(DummyData.menuItems) { item ->
                    // Padding vertikal 6.dp pada setiap item akan menciptakan jarak 12.dp antar item
                    Box(Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                        MenuCard(item)
                    }
                }
            }
        }
    }
}