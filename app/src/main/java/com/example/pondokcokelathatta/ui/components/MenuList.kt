package com.example.pondokcokelathatta.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pondokcokelathatta.model.MenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiFoodBeverage
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.Alignment
import androidx.compose.foundation.shape.RoundedCornerShape

val chocoMenu = listOf(
    MenuItem("Choco Milk", "Espresso with chocolate and steamed milk, often topped with whipped cream.", 25),
    MenuItem("Choco Latte", "Espresso with a small amount of milk or foam", 30),
    MenuItem("Choco Breeze", "Espresso with a generous amount of steamed milk and a small layer of foam.", 28),
    MenuItem("Café con Leche", "Similar to a latte, made with strong coffee and scalded milk.", 24),
    MenuItem("Café Cubano", "Espresso with sugar, creating a sweet and strong coffee.", 20)
)

@Composable
fun MenuList(selectedCategory: String) {
    LazyColumn(modifier = Modifier
        .padding(16.dp)
        .fillMaxHeight()) {
        items(chocoMenu) {
            MenuCard(it)
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
fun MenuCard(item: MenuItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.EmojiFoodBeverage,
                contentDescription = null,
                modifier = Modifier.size(56.dp)
            )
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(item.name, fontWeight = FontWeight.Bold)
                Text(item.description, fontSize = 12.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
            }
            Text("$ ${item.price}", fontWeight = FontWeight.Bold)
        }
    }
}
