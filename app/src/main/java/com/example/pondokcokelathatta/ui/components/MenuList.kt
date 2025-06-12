package com.example.pondokcokelathatta.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pondokcokelathatta.R
import com.example.pondokcokelathatta.model.MenuItem
import com.example.pondokcokelathatta.ui.theme.BrownPrimary
import com.example.pondokcokelathatta.ui.theme.TextPrimary
import com.example.pondokcokelathatta.ui.theme.TextSecondary
import com.example.pondokcokelathatta.ui.theme.WhiteCream

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
    var quantity by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(165.dp) // Menggunakan tinggi original agar tata letak tidak sempit
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
                    .size(107.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )

            // Kolom ini akan mengatur posisi teks, tombol, dan harga
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween // Mendorong konten untuk mengisi ruang
            ) {
                // Konten Atas: Nama dan Deskripsi
                Column(modifier = Modifier.padding(top = 6.dp)) {
                    Text(
                        text = item.name,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        fontSize = 19.sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = item.description,
                        fontSize = 12.sp,
                        color = TextSecondary,
                        lineHeight = 16.sp
                    )
                }

                // Konten Tengah: Tombol "Tambah" atau pemilih kuantitas
                if (quantity == 0) {
                    OutlinedButton(
                        onClick = { quantity++ },
                        modifier = Modifier.height(34.dp),
                        contentPadding = PaddingValues(horizontal = 24.dp)
                    ) {
                        Text("Tambah")
                    }
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Tombol Minus
                        Button(
                            onClick = { if (quantity > 0) quantity-- },
                            modifier = Modifier.size(26.dp),
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BrownPrimary
                            ),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Remove,
                                contentDescription = "Kurangi Kuantitas",
                                tint = WhiteCream
                            )
                        }

                        // Teks Kuantitas
                        Text(
                            text = "$quantity",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = TextPrimary
                        )

                        // Tombol Plus
                        Button(
                            onClick = { quantity++ },
                            modifier = Modifier.size(26.dp),
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BrownPrimary
                            ),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Tambah Kuantitas",
                                tint = WhiteCream
                            )
                        }
                    }
                }

                // Konten Bawah: Harga
                Text(
                    text = "%,d".format(item.price).replace(',', '.'),
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
            }
        }
    }
}