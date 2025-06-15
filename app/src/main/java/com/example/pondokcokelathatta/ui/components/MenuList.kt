package com.example.pondokcokelathatta.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pondokcokelathatta.R
import com.example.pondokcokelathatta.model.MenuItem
import com.example.pondokcokelathatta.ui.theme.BrownPrimary
import com.example.pondokcokelathatta.ui.theme.TextPrimary
import com.example.pondokcokelathatta.ui.theme.TextSecondary
import com.example.pondokcokelathatta.ui.theme.WhiteCream

/**
 * Extension function untuk LazyListScope yang menambahkan daftar item menu.
 * Ini memungkinkan kita untuk memasukkan daftar item ke dalam LazyColumn yang ada.
 */
fun LazyListScope.menuList(
    menuItems: List<MenuItem>,
    onItemClick: (MenuItem) -> Unit
) {
    items(menuItems) { item ->
        // Padding ditambahkan di sini untuk setiap kartu
        Box(Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
            MenuCard(item, onClick = { onItemClick(item) })
        }
    }
}

@Composable
fun MenuCard(item: MenuItem, onClick: () -> Unit) {
    var quantity by remember { mutableIntStateOf(0) }
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (isPressed) 0.98f else 1f, label = "")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(165.dp)
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
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.menu_card),
            contentDescription = "Menu item background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.name,
                modifier = Modifier
                    .size(107.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Column(modifier = Modifier.padding(top = 6.dp)) {
                    Text(
                        text = item.name,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        fontSize = 19.sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = item.description,
                        fontSize = 12.sp,
                        color = TextSecondary,
                        lineHeight = 16.sp
                    )
                }

                Spacer(Modifier.height(8.dp))

                if (quantity == 0) {
                    OutlinedButton(
                        onClick = { quantity++ },
                        modifier = Modifier.height(34.dp),
                        contentPadding = PaddingValues(horizontal = 24.dp)
                    ) {
                        Text("Tambah")
                    }
                } else {
                    OutlinedButton(
                        onClick = { /* Aksi di-handle oleh tombol di dalam */ },
                        modifier = Modifier.height(34.dp),
                        contentPadding = PaddingValues(horizontal = 6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Button(
                                onClick = { if (quantity > 0) quantity-- },
                                modifier = Modifier.size(26.dp),
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = BrownPrimary
                                ),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Remove,
                                    contentDescription = "Kurangi Kuantitas",
                                    tint = WhiteCream,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Text(
                                text = "$quantity",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = TextPrimary
                            )
                            Button(
                                onClick = { quantity++ },
                                modifier = Modifier.size(26.dp),
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = BrownPrimary
                                ),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Tambah Kuantitas",
                                    tint = WhiteCream,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.weight(1f))

                Text(
                    text = "%,d".format(item.price).replace(',', '.'),
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
            }
        }
    }
}