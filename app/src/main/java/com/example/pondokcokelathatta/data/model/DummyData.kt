package com.example.pondokcokelathatta.data.model

import com.example.pondokcokelathatta.R
import com.example.pondokcokelathatta.model.MenuItem

/**
 * Dalam aplikasi nyata, data ini akan datang dari ViewModel yang terhubung ke Firebase.
 */
object DummyData {

    // Definisikan semua item menu yang mungkin ada di sini.
    private val chocoMilk = MenuItem(
        name = "Choco Milk",
        description = "Espresso dengan cokelat dan susu steamed.",
        price = 13000,
        imageRes = R.drawable.choco_milk // Pastikan drawable ini ada
    )

    private val chocoLatte = MenuItem(
        name = "Choco Latte",
        description = "Espresso dengan sedikit susu atau busa.",
        price = 15000, // Harga bisa dibedakan
        imageRes = R.drawable.choco_milk // Ganti dengan gambar yang sesuai jika ada
    )

    private val chocoBreeze = MenuItem(
        name = "Choco Breeze",
        description = "Espresso dengan banyak susu steamed.",
        price = 14000, // Harga bisa dibedakan
        imageRes = R.drawable.choco_milk // Ganti dengan gambar yang sesuai jika ada
    )

    // Tambahkan item menu lainnya di sini jika perlu
    // ...

    /**
     * Daftar semua item menu yang tersedia.
     * Ini akan menjadi sumber utama untuk ditampilkan di daftar menu lengkap.
     */
    val menuItems: List<MenuItem> = listOf(
        chocoMilk,
        chocoLatte,
        chocoBreeze
        // tambahkan item menu lainnya di sini
    )

    /**
     * Daftar item yang direkomendasikan.
     * Ambil beberapa item dari daftar menu di atas untuk dijadikan rekomendasi.
     */
    val recommendations: List<MenuItem> = listOf(
        chocoLatte, // Contoh: Choco Latte adalah item rekomendasi
        chocoMilk   // Contoh: Choco Milk juga rekomendasi
    )
}