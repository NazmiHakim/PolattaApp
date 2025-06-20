package com.example.pondokcokelathatta.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pondokcokelathatta.ui.components.MenuCard
import com.example.pondokcokelathatta.ui.viewmodel.MenuViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CheckoutScreen(
    menuViewModel: MenuViewModel,
    modifier: Modifier = Modifier
) {
    val orderedItems by menuViewModel.orderedItems.collectAsState()
    val totalPrice by menuViewModel.totalPrice.collectAsState()
    val finalPrice = totalPrice

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                // Menambahkan padding di bagian bawah agar tidak tertutup oleh tombol
                .padding(bottom = 80.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            // Judul "Order Details"
            item {
                Text(
                    "Order Details",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // Daftar item yang dipesan
            items(orderedItems.toList()) { (item, quantity) ->
                MenuCard(
                    item = item,
                    quantity = quantity,
                    onIncrease = { menuViewModel.increaseQuantity(item) },
                    onDecrease = { menuViewModel.decreaseQuantity(item) },
                    onClick = { } // Biarkan kosong agar tidak navigasi dari halaman ini
                )
                Spacer(Modifier.height(8.dp))
            }

            // Rincian Pembayaran
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text("Payment summary", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))
                PaymentDetailRow("Price", totalPrice)
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                PaymentDetailRow("Total payment", finalPrice, isTotal = true)
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // Tombol ini sekarang berada di dalam Box dan akan tampil di atas konten LazyColumn
        Button(
            onClick = { /* TODO: Logika untuk memesan */ },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            shape = RoundedCornerShape(12.dp),
        ) {
            Text("Place delivery order", modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

// Composable ini tidak berubah
@Composable
fun PaymentDetailRow(label: String, amount: Int, isDiscount: Boolean = false, isTotal: Boolean = false) {
    val formatter = NumberFormat.getNumberInstance(Locale("in", "ID"))
    val amountColor = if (isDiscount) Color(0xFF00880F) else MaterialTheme.colorScheme.onSurface
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal,
            color = amountColor
        )
        Text(
            text = "Rp${formatter.format(amount)}",
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal,
            color = amountColor
        )
    }
}