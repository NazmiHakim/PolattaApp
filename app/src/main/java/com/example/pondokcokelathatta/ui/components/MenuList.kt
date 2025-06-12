package com.example.pondokcokelathatta.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
    // Box digunakan untuk menumpuk gambar latar belakang dan konten.
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(126.dp) // Menggunakan tinggi 126dp sesuai permintaan Anda.
    ) {
        // Gambar latar belakang untuk kartu menu.
        Image(
            // Pastikan Anda telah menambahkan 'menu_card.png' ke folder drawable Anda.
            painter = painterResource(id = R.drawable.menu_card),
            contentDescription = "Menu item background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds // Memastikan gambar mengisi seluruh area Box.
        )

        // Konten (gambar minuman, teks, tombol) ditempatkan di atas latar belakang.
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
                    .size(80.dp)
                    .padding(end = 12.dp),
                contentScale = ContentScale.Fit
            )

            // Kolom untuk nama, deskripsi, dan harga.
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
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 16.sp
                )
                Spacer(Modifier.height(8.dp))
                // Memformat harga agar sesuai dengan format "Rp 13.000"
                Text(
                    text = "Rp ${"%,d".format(item.price).replace(',', '.')}",
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    fontSize = 14.sp
                )
            }

            // Tombol plus di sebelah kanan.
            IconButton(
                onClick = { /* TODO: Tambahkan logika untuk aksi tombol ini */ },
                modifier = Modifier.size(32.dp)
            ) {
                Image(
                    // Pastikan Anda telah menambahkan 'button_plus.png' ke folder drawable Anda.
                    painter = painterResource(id = R.drawable.button_plus),
                    contentDescription = "Add"
                )
            }
        }
    }
}