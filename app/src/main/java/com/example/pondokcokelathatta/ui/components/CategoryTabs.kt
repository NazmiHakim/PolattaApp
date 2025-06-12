package com.example.pondokcokelathatta.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoryTabs(selected: MutableState<String>, modifier: Modifier = Modifier) {
    val tabs = listOf("Choco Series", "Cimory Series", "Matcha", "Choco Boo", "Basreng")
    ScrollableTabRow(
        selectedTabIndex = tabs.indexOf(selected.value),
        modifier = modifier,
        edgePadding = 16.dp,
        indicator = {}, // Menghilangkan garis hitam lurus (indikator)
        divider = {}    // Menghilangkan garis pembatas di bawah TabRow
    ) {
        tabs.forEach { tab ->
            Tab(
                selected = selected.value == tab,
                onClick = { selected.value = tab },
                text = { Text(tab, fontSize = 14.sp) }
                // Catatan: Efek ripple saat klik adalah bagian dari perilaku default Material Design
                // untuk komponen Tab dan tidak dapat dihilangkan dengan mudah tanpa
                // membuat implementasi Tab kustom, karena API standar tidak menyediakan
                // parameter untuk menonaktifkannya. Perubahan di atas sudah menghilangkan
                // garis indikator sesuai permintaan.
            )
        }
    }
}