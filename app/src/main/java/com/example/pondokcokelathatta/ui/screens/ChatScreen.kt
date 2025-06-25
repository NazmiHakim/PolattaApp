package com.example.pondokcokelathatta.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// Data class untuk merepresentasikan satu pesan
data class ChatMessage(
    val text: String,
    val isFromUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    onBack: () -> Unit
) {
    val adminName = "BDJ-16"
    var textState by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val listState = rememberLazyListState()

    // Daftar pesan yang disimulasikan
    val messages = remember {
        mutableStateListOf(
            ChatMessage("Halo! Ada yang bisa saya bantu terkait pesanan Anda?", isFromUser = false)
        )
    }

    // Efek untuk auto-reply dari admin
    LaunchedEffect(messages.size) {
        if (messages.lastOrNull()?.isFromUser == true) {
            // Scroll ke pesan terakhir
            listState.animateScrollToItem(messages.size - 1)
            // Tunda balasan admin
            delay(1500)
            messages.add(ChatMessage("Baik, pesanan Anda sedang kami proses. Mohon ditunggu ya.", isFromUser = false))
            // Scroll lagi setelah admin membalas
            listState.animateScrollToItem(messages.size - 1)
        }
    }


    fun sendMessage() {
        if (textState.isNotBlank()) {
            messages.add(ChatMessage(textState, isFromUser = true))
            textState = ""
            keyboardController?.hide()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat with $adminName") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        bottomBar = {
            // Input field dan tombol kirim
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding() // Menambahkan padding untuk navigation bar
                    .imePadding() // Menambahkan padding untuk keyboard
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = textState,
                    onValueChange = { textState = it },
                    placeholder = { Text("Ketik pesan...") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Send
                    ),
                    keyboardActions = KeyboardActions(
                        onSend = { sendMessage() }
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = { sendMessage() },
                    enabled = textState.isNotBlank(),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                    )
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send Message")
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            items(messages) { message ->
                ChatMessageBubble(message = message)
            }
        }
    }
}

@Composable
fun ChatMessageBubble(message: ChatMessage) {
    val horizontalArrangement = if (message.isFromUser) Arrangement.End else Arrangement.Start
    val bubbleColor = if (message.isFromUser) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
    val textColor = if (message.isFromUser) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = horizontalArrangement
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f) // Pesan tidak akan memenuhi seluruh lebar
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = horizontalArrangement
            ) {
                Box(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 16.dp,
                                bottomStart = if (message.isFromUser) 16.dp else 0.dp,
                                bottomEnd = if (message.isFromUser) 0.dp else 16.dp
                            )
                        )
                        .background(bubbleColor)
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = message.text,
                        color = textColor,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}