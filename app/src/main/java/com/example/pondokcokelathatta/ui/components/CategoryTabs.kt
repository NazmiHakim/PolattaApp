package com.example.pondokcokelathatta.ui.components

import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pondokcokelathatta.ui.theme.BrownPrimary
import com.example.pondokcokelathatta.ui.theme.TextSecondary

@Composable
fun CategoryTabs(selected: MutableState<String>, modifier: Modifier = Modifier) {
    val tabs = listOf("Choco Series", "Cimory Series", "Matcha", "Choco Boo", "Basreng")
    val selectedIndex = tabs.indexOf(selected.value)
    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        modifier = modifier,
        edgePadding = 16.dp,
        indicator = { tabPositions ->
            SecondaryIndicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedIndex]),
                height = 2.dp,
                color = BrownPrimary // Menggunakan warna cokelat dari theme
            )
        },
        divider = { }
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                selected = selectedIndex == index,
                onClick = { selected.value = tab },
                text = { Text(tab, fontSize = 14.sp) },
                selectedContentColor = BrownPrimary,
                unselectedContentColor = TextSecondary
            )
        }
    }
}