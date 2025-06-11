package com.example.pondokcokelathatta.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun RecommendationSection() {
    Column(Modifier.padding(16.dp)) {
        Text("Rekomendasi", fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        LazyRow {
            items(listOf("Choco Milk", "Choco Latte", "Choco Boo")) {
                RecommendationCard(it)
            }
        }
    }
}

@Composable
fun RecommendationCard(title: String) {
    Card(
        modifier = Modifier
            .size(width = 120.dp, height = 160.dp)
            .padding(end = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD7A477))

    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Default.LocalCafe, contentDescription = null, modifier = Modifier.size(64.dp))
            Text(title, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
        }
    }
}
