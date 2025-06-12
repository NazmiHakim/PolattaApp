package com.example.pondokcokelathatta.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pondokcokelathatta.R
import com.example.pondokcokelathatta.model.MenuItem
import com.example.pondokcokelathatta.ui.theme.TextPrimary
import com.example.pondokcokelathatta.ui.theme.TextSecondary

@Composable
fun MenuList(menuItems: List<MenuItem>) {
    Spacer(Modifier.height(12.dp))
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(menuItems) { item ->
            MenuCard(item)
        }
    }
}

@Composable
fun MenuCard(item: MenuItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.menu_card),
            contentDescription = "Menu item background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.name,
                modifier = Modifier
                    .size(95.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    fontSize = 16.sp
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = item.description,
                    fontSize = 12.sp,
                    color = TextSecondary,
                    lineHeight = 16.sp
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Rp ${"%,d".format(item.price).replace(',', '.')}",
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    fontSize = 14.sp
                )
            }

            // --- PERUBAHAN DI SINI ---
            // Mengganti IconButton dengan komponen PlusButton yang baru dibuat.
            PlusButton(
                onClick = { /* TODO: Tambahkan logika untuk aksi tombol ini */ },
                modifier = Modifier.size(30.dp)
            )
        }
    }
}