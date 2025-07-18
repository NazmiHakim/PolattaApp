package com.example.pondokcokelathatta.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pondokcokelathatta.ui.theme.TextPrimary

@Composable
fun TopBar(onChatClick: () -> Unit) { // MODIFIKASI: Tambahkan parameter onChatClick
    Row(
        modifier = Modifier
            .fillMaxWidth()
            // Menambahkan padding atas untuk status bar
            .padding(top = 17.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Polatta", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Spacer(modifier = Modifier.weight(1f)) // Mendorong ikon ke kanan
        IconButton(onClick = onChatClick) { // MODIFIKASI: Panggil onChatClick di sini
            Icon(
                imageVector = Icons.Outlined.ChatBubbleOutline,
                contentDescription = "Message",
                tint = TextPrimary,
                modifier = Modifier.scale(scaleX = -1f, scaleY = 1f) // Menambahkan modifier untuk membalik gambar
            )
        }
    }
}