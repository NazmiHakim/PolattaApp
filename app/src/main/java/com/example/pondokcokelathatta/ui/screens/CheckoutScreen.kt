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
import androidx.navigation.NavController
import com.example.pondokcokelathatta.ui.components.MenuCard
import com.example.pondokcokelathatta.ui.navigation.Screen
import com.example.pondokcokelathatta.ui.viewmodel.MenuViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CheckoutScreen(
    menuViewModel: MenuViewModel,
    navController: NavController, // Tambahkan NavController
    modifier: Modifier = Modifier
) {
    val orderedItems by menuViewModel.orderedItems.collectAsState()
    val totalPrice by menuViewModel.totalPrice.collectAsState()
    val finalPrice = totalPrice

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
        ) {
            item {
                Text(
                    "Order Details",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            items(orderedItems.toList()) { (item, quantity) ->
                MenuCard(
                    item = item,
                    quantity = quantity,
                    onIncrease = { menuViewModel.increaseQuantity(item) },
                    onDecrease = { menuViewModel.decreaseQuantity(item) },
                    onClick = { }
                )
                Spacer(Modifier.height(8.dp))
            }

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

        // Tombol diubah untuk memproses pesanan dan navigasi
        Button(
            onClick = {
                menuViewModel.placeOrder()
                navController.navigate(Screen.Status.route) {
                    // Membersihkan backstack hingga ke home
                    popUpTo(Screen.Home.route)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00880F),
                contentColor = Color.White
            ),
            enabled = orderedItems.isNotEmpty() // Tombol aktif hanya jika ada item
        ) {
            Text("Place delivery order", color = Color.White, modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

// Composable PaymentDetailRow tidak berubah
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