package com.example.pondokcokelathatta.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatusScreenContent(modifier: Modifier = Modifier) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    // Tabs ordered with "Ongoing" first, as requested.
    val tabs = listOf("Ongoing", "History")

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
            0 -> OngoingTab()
            1 -> HistoryTab()
            2 -> EmptyState("No scheduled orders yet.")
            3 -> EmptyState("No drafts saved.")
        }
    }
}

@Composable
fun OngoingTab() {
    // This UI is based on 'WhatsApp Image 2025-06-24 at 15.33.23_937633e4.jpg'
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Please add the image 'img_ongoing_placeholder.png' to your drawable resources
//        Image(
//            painter = painterResource(id = R.drawable.img_ongoing_placeholder),
//            contentDescription = "No ongoing orders",
//            modifier = Modifier.width(250.dp)
//        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Ayo Pesan Sekarang!",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Kami Siap Menerima Pesananmu.",
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}

@Composable
fun HistoryTab() {
    // This UI is based on 'WhatsApp Image 2025-06-24 at 15.33.23_66771bca.jpg'
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Belum Ada Histori Pesanan",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Pesan Sekarang Yuk!",
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
//            HistoryItemCard()
        }
    }
}
                // Please add the image 'history_item_image.png' to your drawable resources
//                Image(
//                    painter = painterResource(id = R.drawable.history_item_image),
//                    contentDescription = "RUMAH MAKAN UDA SAYANG",
//                    modifier = Modifier
//                        .size(60.dp)
//                        .clip(RoundedCornerShape(8.dp)),
//                    contentScale = ContentScale.Crop
//                )

@Composable
fun EmptyState(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(message, fontSize = 18.sp, color = Color.Gray)
    }
}