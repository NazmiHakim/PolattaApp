package com.example.pondokcokelathatta.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pondokcokelathatta.R

@Composable
fun CustomerCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit // ADD THIS PARAMETER
) {
    Box(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick), // ADD THIS MODIFIER
        contentAlignment = Alignment.Center
    ) {
            Image(
                // GANTI INI dengan resource `user_card` Anda setelah diunggah, contoh: R.drawable.user_card
                painter = painterResource(id = R.drawable.user_card),
                contentDescription = "Customer Card Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Sapaan dan Nama Pelanggan
                Column {
                    Text(
                        text = "Selamat Pagi,",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "Nazmi",
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Poin
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text = "Poin",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "2.475",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }