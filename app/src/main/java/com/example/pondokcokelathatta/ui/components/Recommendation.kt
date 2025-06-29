package com.example.pondokcokelathatta.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pondokcokelathatta.R
import com.example.pondokcokelathatta.model.MenuItem

@Composable
fun RecommendationSection(recommendations: List<MenuItem>, onItemClick: (MenuItem) -> Unit) { // Tambahkan parameter onItemClick
    Column {
        Spacer(Modifier.height(18.dp))
        Text(
            "Rekomendasi",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(recommendations) { item ->
                RecommendationCard(item, onClick = { onItemClick(item) }) // Pass click listener
            }
        }
    }
}

@Composable
fun RecommendationCard(item: MenuItem, onClick: () -> Unit) { // Tambahkan parameter onClick
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f, label = "")
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(180.dp)
            .scale(scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    },
                    onTap = { onClick() }
                )
            }, // Tambahkan modifier clickable
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.recommendation), // Ini adalah gambar latar belakang baru
                contentDescription = "Recommendation Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = item.name,
                    modifier = Modifier
                        .height(140.dp)
                        .fillMaxWidth()
                        .padding(bottom = 1.dp),
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = item.name,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color.White, // Teks tetap putih
                    modifier = Modifier
                        .padding(bottom = 14.dp)
                )
            }
        }
    }
}