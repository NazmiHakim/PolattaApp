package com.example.pondokcokelathatta.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.pondokcokelathatta.ui.components.BottomNavBar
import com.example.pondokcokelathatta.ui.components.CategoryTabs
import com.example.pondokcokelathatta.ui.components.MenuList
import com.example.pondokcokelathatta.ui.components.RecommendationSection
import com.example.pondokcokelathatta.ui.components.SearchBar
import com.example.pondokcokelathatta.ui.components.TopBar

@Composable
fun PolattaScreen() {
    val selectedTab = remember { mutableStateOf("Choco Series") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopBar()
        SearchBar()
        RecommendationSection()
        CategoryTabs(selectedTab)
        MenuList(selectedTab.value)
    }

    BottomNavBar()
}
