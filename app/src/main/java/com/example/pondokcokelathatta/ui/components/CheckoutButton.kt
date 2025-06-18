package com.example.pondokcokelathatta.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CheckoutButton(
    itemCount: Int,
    totalPrice: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Format harga ke dalam format Rupiah
    val formatter = NumberFormat.getNumberInstance(Locale("in", "ID"))

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(50), // Sudut membulat penuh
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00880F)), // Warna hijau dari gambar Gojek
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "$itemCount item${if (itemCount > 1) "s" else ""}",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Rp${formatter.format(totalPrice)}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    imageVector = Icons.Default.ShoppingBasket,
                    contentDescription = "Checkout",
                    tint = Color.White
                )
            }
        }
    }
}