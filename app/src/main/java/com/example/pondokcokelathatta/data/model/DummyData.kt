package com.example.pondokcokelathatta.data.model

import com.example.pondokcokelathatta.R
import com.example.pondokcokelathatta.model.MenuItem

/**
 * Dalam aplikasi nyata, data ini akan datang dari ViewModel yang terhubung ke Firebase.
 */
object DummyData {

    // Daftar item yang direkomendasikan
    val recommendations = listOf(
        MenuItem(
            name = "Choco Milk",
            description = "Espresso dengan cokelat dan susu steamed.",
            price = 25,
            imageRes = R.drawable.choco_milk // Ganti dengan drawable Anda
        ),
        MenuItem(
            name = "Choco Latte",
            description = "Espresso dengan sedikit susu atau busa.",
            price = 30,
            imageRes = R.drawable.choco_milk // Ganti dengan drawable Anda
        ),
        MenuItem(
            name = "Choco Breeze",
            description = "Espresso dengan banyak susu steamed.",
            price = 28,
            imageRes = R.drawable.choco_milk // Ganti dengan drawable Anda
        )
    )

    // Daftar semua item menu yang tersedia.
    val menuItems = listOf(
        MenuItem(
            name = "Choco Milk",
            description = "Espresso dengan cokelat dan susu steamed...",
            price = 25,
            imageRes = R.drawable.choco_milk // Ganti dengan drawable Anda
        ),
        MenuItem(
            name = "Choco Latte",
            description = "Espresso dengan sedikit susu atau busa.",
            price = 30,
            imageRes = R.drawable.choco_milk // Ganti dengan drawable Anda
        ),
        MenuItem(
            name = "Choco Breeze",
            description = "Espresso dengan banyak susu steamed.",
            price = 28,
            imageRes = R.drawable.choco_milk // Ganti dengan drawable Anda
        ),
        MenuItem(
            name = "Café con Leche",
            description = "Mirip dengan latte, dibuat dengan kopi kental.",
            price = 24,
            imageRes = R.drawable.choco_milk // Ganti dengan drawable Anda
        ),
        MenuItem(
            name = "Café Cubano",
            description = "Espresso dengan gula, menghasilkan kopi manis.",
            price = 20,
            imageRes = R.drawable.choco_milk // Ganti dengan drawable Anda
        )
    )
}