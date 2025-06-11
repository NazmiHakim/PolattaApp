package com.example.pondokcokelathatta.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pondokcokelathatta.ui.theme.TextPrimary

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            // Menambahkan padding atas untuk status bar
            .padding(top = 17.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Polatta", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
    }
}