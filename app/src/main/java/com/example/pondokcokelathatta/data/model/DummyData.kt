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
            price = 25000,
            imageRes = R.drawable.choco_milk // Ganti dengan drawable Anda
        ),
        MenuItem(
            name = "Choco Latte",
            description = "Espresso dengan sedikit susu atau busa.",
            price = 30000,
            imageRes = R.drawable.choco_milk // Ganti dengan drawable Anda
        ),
        MenuItem(
            name = "Choco Breeze",
            description = "Espresso dengan banyak susu steamed.",
            price = 28000,
            imageRes = R.drawable.choco_milk // Ganti dengan drawable Anda
        )
    )

    // Daftar semua item menu yang tersedia (diperbarui sesuai screenshot).
    val menuItems = listOf(
        MenuItem(
            name = "Choco Milk",
            description = "Double espresso and water, served cold",
            price = 13000,
            imageRes = R.drawable.choco_milk // Pastikan drawable ini ada
        ),
        MenuItem(
            name = "Choco Milk",
            description = "Double espresso and water, served cold",
            price = 13000,
            imageRes = R.drawable.choco_milk // Pastikan drawable ini ada
        ),
        MenuItem(
            name = "Choco Milk",
            description = "Double espresso and water, served cold",
            price = 13000,
            imageRes = R.drawable.choco_milk // Pastikan drawable ini ada
        ),
        MenuItem(
            name = "Choco Milk",
            description = "Double espresso and water, served cold",
            price = 13000,
            imageRes = R.drawable.choco_milk // Pastikan drawable ini ada
        )
    )
}