package com.example.pondokcokelathatta.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pondokcokelathatta.model.MenuItem
import com.example.pondokcokelathatta.ui.theme.OrangeAccent
import com.example.pondokcokelathatta.ui.theme.TextPrimary

@Composable
fun MenuList(menuItems: List<MenuItem>) {
    Spacer(Modifier.height(12.dp))
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(menuItems) { item ->
            MenuCard(item)
        }
    }
}

@Composable
fun MenuCard(item: MenuItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = OrangeAccent.copy(alpha = 0.3f)) // Warna kartu lebih soft
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.name,
                modifier = Modifier
                    .size(64.dp)
                    .padding(4.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(item.name, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text(
                    item.description,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = TextPrimary.copy(alpha = 0.7f)
                )
            }
            Text("$ ${item.price}", fontWeight = FontWeight.Bold, color = TextPrimary)
        }
    }
}