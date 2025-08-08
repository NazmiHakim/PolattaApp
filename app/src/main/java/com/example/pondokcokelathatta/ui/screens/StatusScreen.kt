package com.example.pondokcokelathatta.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pondokcokelathatta.model.Order
import com.example.pondokcokelathatta.model.OrderStatus
import com.example.pondokcokelathatta.ui.viewmodel.AuthViewModel
import com.example.pondokcokelathatta.ui.viewmodel.MenuViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun StatusScreenContent(
    modifier: Modifier = Modifier,
    menuViewModel: MenuViewModel
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Ongoing", "History")
    val ongoingOrders by menuViewModel.ongoingOrders.collectAsState()
    val historyOrders by menuViewModel.historyOrders.collectAsState()
    var showCancelDialog by remember { mutableStateOf<Order?>(null) }
    var cancellationReason by remember { mutableStateOf("") }
    val authState by menuViewModel.authViewModel.authState.collectAsState()
    val isAdmin = (authState as? AuthViewModel.AuthState.Authenticated)?.role == "admin"

    if (showCancelDialog != null) {
        AlertDialog(
            onDismissRequest = { showCancelDialog = null },
            title = { Text("Batalkan Pesanan") },
            text = {
                Column {
                    Text("Apakah Anda yakin ingin membatalkan pesanan ini?")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = cancellationReason,
                        onValueChange = { cancellationReason = it },
                        label = { Text("Alasan Pembatalan") }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showCancelDialog?.let { order ->
                            menuViewModel.cancelOrder(order)
                        }
                        showCancelDialog = null
                        cancellationReason = ""
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    )
                ) {
                    Text("Ya, Batalkan", color = Color.White)
                }
            },
            dismissButton = {
                Button(onClick = { showCancelDialog = null }) {
                    Text("Tidak", color = Color.White)
                }
            }
        )
    }


    Column(modifier = modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
        when (selectedTabIndex) {
            0 -> OngoingTab(
                orders = ongoingOrders,
                onCompleteClick = { order -> menuViewModel.completeOrder(order) },
                onCancelClick = { order -> showCancelDialog = order },
                isAdmin = isAdmin
            )
            1 -> HistoryTab(orders = historyOrders)
        }
    }
}

@Composable
fun OngoingTab(
    orders: List<Order>,
    onCompleteClick: (Order) -> Unit,
    onCancelClick: (Order) -> Unit,
    isAdmin: Boolean
) {
    if (orders.isEmpty()) {
        EmptyState(
            title = "Ayo Pesan Sekarang!",
            subtitle = "Kami Siap Menerima Pesananmu."
        )
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(orders) { order ->
                OrderCard(
                    order = order,
                    onActionClick = { onCompleteClick(order) },
                    actionLabel = "Selesai",
                    onCancelClick = { onCancelClick(order) },
                    isAdmin = isAdmin
                )
            }
        }
    }
}

@Composable
fun HistoryTab(orders: List<Order>) {
    if (orders.isEmpty()) {
        EmptyState(
            title = "Belum Ada Histori Pesanan",
            subtitle = "Pesan Sekarang Yuk!"
        )
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(orders) { order ->
                OrderCard(order = order, actionLabel = "Pesan Lagi", onActionClick = {
                    // Logika untuk memesan lagi bisa ditambahkan di sini
                })
            }
        }
    }
}

@Composable
fun OrderCard(
    order: Order,
    actionLabel: String,
    onActionClick: () -> Unit,
    onCancelClick: (() -> Unit)? = null,
    isAdmin: Boolean = false
) {
    val formatter = NumberFormat.getNumberInstance(Locale("in", "ID"))

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Pesanan #${order.id.take(6)}", // Menampilkan 6 karakter pertama ID
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            order.items.forEach { (menuItem, quantity) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "${quantity}x ${menuItem.name}")
                    Text(text = "Rp${formatter.format(menuItem.price * quantity)}")
                }
                Spacer(modifier = Modifier.height(4.dp))
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Total Harga", fontWeight = FontWeight.Bold)
                Text(
                    "Rp${formatter.format(order.totalPrice)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Status", fontWeight = FontWeight.Bold)
                Text(
                    text = if (order.status == com.example.pondokcokelathatta.model.OrderStatus.ONGOING) "Siap Diambil" else "Selesai",
                    fontWeight = FontWeight.Bold,
                    color = if (order.status == com.example.pondokcokelathatta.model.OrderStatus.ONGOING) Color(0xFF00880F) else Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (onCancelClick != null && order.status == OrderStatus.ONGOING) {
                    Button(
                        onClick = onCancelClick,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Cancel", color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }

                if (isAdmin || order.status != OrderStatus.ONGOING) {
                    Button(
                        onClick = onActionClick,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (order.status == OrderStatus.ONGOING) Color(0xFF00880F) else MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        )
                    ) {
                        Text(actionLabel, color = Color.White)
                    }
                }
            }
        }
    }
}


@Composable
fun EmptyState(title: String, subtitle: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = subtitle,
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }
    }
}