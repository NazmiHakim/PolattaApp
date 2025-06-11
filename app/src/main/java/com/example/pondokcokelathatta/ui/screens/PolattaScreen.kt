package com.example.pondokcokelathatta.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pondokcokelathatta.data.model.DummyData
import com.example.pondokcokelathatta.ui.components.BottomNavBar
import com.example.pondokcokelathatta.ui.components.CategoryTabs
import com.example.pondokcokelathatta.ui.components.MenuList
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding) // Terapkan padding dari Scaffold
            ) {
                TopBar()
                SearchBar()
                RecommendationSection(DummyData.recommendations)
                // Tambahkan teks "Menu" sesuai desain
                androidx.compose.material3.Text(
                    text = "Menu",
                    style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )
                CategoryTabs(selectedTab)
                // FIX: Hapus .toString() agar tipe data yang dikirim sesuai (List<MenuItem>)
                MenuList(DummyData.menuItems)
            }
        }
    }
}