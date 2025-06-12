package com.example.pondokcokelathatta.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pondokcokelathatta.ui.theme.BrownPrimary
import com.example.pondokcokelathatta.ui.theme.WhiteCream

/**
 * Komponen Tombol kustom berbentuk lingkaran dengan ikon plus di tengah.
 * Tombol ini meniru tampilan dari gambar `button_plus.png`.
 *
 * @param onClick Aksi yang akan dijalankan ketika tombol diklik.
 * @param modifier Modifier untuk kustomisasi dari luar.
 */
@Composable
fun PlusButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape, // Membuat tombol menjadi bulat sempurna
        colors = ButtonDefaults.buttonColors(
            containerColor = BrownPrimary // Menggunakan warna coklat dari tema Anda
        ),
        // Menghilangkan padding default agar ikon bisa lebih besar dan terpusat
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add",
            tint = WhiteCream, // Memberi warna cream pada ikon plus
            modifier = Modifier.size(24.dp) // Menyesuaikan ukuran ikon plus di dalam tombol
        )
    }
}