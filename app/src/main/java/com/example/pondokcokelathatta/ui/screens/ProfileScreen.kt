package com.example.pondokcokelathatta.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.ListAlt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pondokcokelathatta.ui.navigation.Screen
import com.example.pondokcokelathatta.ui.viewmodel.AuthViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    val authState by authViewModel.authState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        // Profile Info Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEAF9E9)), // Light green background for avatar
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "NH",
                    color = Color(0xFF16881A), // Darker green for text
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Nazmi Hakim", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("woodperson123@gmail.com", color = Color.Gray, fontSize = 14.sp)
                Text("+6281959001137", color = Color.Gray, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Poin: 2.475", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            }
            IconButton(onClick = { navController.navigate(Screen.EditProfile.route) }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Profile")
            }
        }

        HorizontalDivider()

        // Menu Items Section
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            ProfileMenuItem(
                icon = Icons.Outlined.ListAlt,
                title = "My activity",
                subtitle = "See ongoing & history",
                onClick = { /* TODO: Navigate to Activity History */ }
            )
            ProfileMenuItem(
                icon = Icons.Outlined.HelpOutline,
                title = "Help center",
                onClick = { /* TODO: Navigate to Help Center */ }
            )

            // Tampilkan menu admin jika pengguna adalah admin
            if (authState is AuthViewModel.AuthState.Authenticated && (authState as AuthViewModel.AuthState.Authenticated).role == "admin") {
                ProfileMenuItem(
                    icon = Icons.Default.AdminPanelSettings,
                    title = "Admin Dashboard",
                    subtitle = "Manage application data",
                    onClick = { navController.navigate(Screen.Admin.route) }
                )
            }
        }
    }
}

@Composable
private fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title, modifier = Modifier.size(24.dp), tint = Color.Gray)
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontSize = 16.sp)
            if (subtitle != null) {
                Text(subtitle, fontSize = 14.sp, color = Color.Gray)
            }
        }
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.Gray)
    }
}