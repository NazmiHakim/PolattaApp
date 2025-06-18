package com.example.pondokcokelathatta.data.model

import com.example.pondokcokelathatta.R
import com.example.pondokcokelathatta.model.MenuItem

/**
 * Dalam aplikasi nyata, data ini akan datang dari ViewModel yang terhubung ke Firebase.
 */
object DummyData {

    // Mendefinisikan satu sumber data untuk semua item menu untuk memastikan konsistensi.
    private val allMenuItems = listOf(
        MenuItem(
            name = "Choco Milk",
            description = "Espresso dengan cokelat dan susu steamed.",
            price = 13000, // Harga diperbarui
            imageRes = R.drawable.choco_milk // Pastikan drawable ini ada
        ),
        MenuItem(
            name = "Choco Latte",
            description = "Espresso dengan sedikit susu atau busa.",
            price = 13000, // Harga diperbarui
            imageRes = R.drawable.choco_milk // Pastikan drawable ini ada
        ),
        MenuItem(
            name = "Choco Breeze",
            description = "Espresso dengan banyak susu steamed.",
            price = 13000, // Harga diperbarui
            imageRes = R.drawable.choco_milk // Pastikan drawable ini ada
        )
    )

    // Daftar item yang direkomendasikan sekarang mengambil dari sumber data utama.
    val recommendations: List<MenuItem> = allMenuItems

    // Daftar semua item menu yang tersedia juga mengambil dari sumber data utama.
    val menuItems: List<MenuItem> = allMenuItems
}