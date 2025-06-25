package com.example.pondokcokelathatta.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pondokcokelathatta.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    navController: NavController,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pesan") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                ChatListItem(
                    chatName = "BDJ-16",
                    lastMessage = "Halo! Ada yang bisa saya bantu terkait pesanan Anda?",
                    timestamp = "13:45", // Contoh timestamp
                    onClick = {
                        // Navigasi ke halaman detail chat
                        navController.navigate(Screen.ChatDetail.route)
                    }
                )
            }
        }
    }
}

@Composable
fun ChatListItem(
    chatName: String,
    lastMessage: String,
    timestamp: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = chatName.firstOrNull()?.toString() ?: "A",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Nama dan Pesan Terakhir
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = chatName,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = lastMessage,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Timestamp
        Text(
            text = timestamp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 12.sp
        )
    }
    HorizontalDivider(modifier = Modifier.padding(start = 88.dp, end = 16.dp))
}