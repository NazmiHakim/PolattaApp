package com.example.pondokcokelathatta.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    menuViewModel: MenuViewModel,
    onBack: () -> Unit,
) {
    val orderedItems by menuViewModel.orderedItems.collectAsState()
    val totalPrice by menuViewModel.totalPrice.collectAsState()
    val finalPrice = totalPrice

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Payment Summary", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            // Tombol "Place delivery order" di bagian bawah
            Button(
                onClick = { /* TODO: Logika untuk memesan */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 32.dp),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text("Place delivery order", modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
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
    }
}

// Composable untuk menampilkan setiap baris detail pembayaran
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