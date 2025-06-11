package com.example.pondokcokelathatta.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.sp

@Composable
fun CategoryTabs(selected: MutableState<String>) {
    val tabs = listOf("Choco Series", "Cimory Series", "Matcha", "Choco Boo", "Basreng")
    ScrollableTabRow(selectedTabIndex = tabs.indexOf(selected.value)) {
        tabs.forEach { tab ->
            Tab(
                selected = selected.value == tab,
                onClick = { selected.value = tab },
                text = { Text(tab, fontSize = 14.sp) }
            )
        }
    }
}
