package com.example.pondokcokelathatta.model

import androidx.annotation.DrawableRes

data class MenuItem(
    val name: String,
    val description: String,
    val price: Int,
    @DrawableRes val imageRes: Int, // Tambahkan properti untuk resource gambar
    val category: String
)