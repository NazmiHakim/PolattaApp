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
        imageRes = R.drawable.choco_milk, // Pastikan drawable ini ada
        category = "Choco Series"
    )

    private val chocoLatte = MenuItem(
        name = "Choco Latte",
        description = "Espresso dengan sedikit susu atau busa.",
        price = 15000, // Harga bisa dibedakan
        imageRes = R.drawable.choco_latte, // Ganti dengan gambar yang sesuai jika ada
        category = "Choco Series"
    )

    private val chocoBreeze = MenuItem(
        name = "Choco Breeze",
        description = "Espresso dengan banyak susu steamed.",
        price = 14000, // Harga bisa dibedakan
        imageRes = R.drawable.choco_breeze, // Ganti dengan gambar yang sesuai jika ada
        category = "Choco Series"
    )

    private val chocoMint = MenuItem(
        name = "Choco Mint",
        description = "Minuman cokelat dengan sensasi dingin dari mint.",
        price = 15000,
        imageRes = R.drawable.choco_mint,
        category = "Choco Series"
    )

    private val cocoaBlackCookies = MenuItem(
        name = "Cocoa Black Cookies",
        description = "Minuman cokelat dengan campuran biskuit hitam yang renyah.",
        price = 16000,
        imageRes = R.drawable.blc,
        category = "Choco Series"
    )

    private val chocoMilko = MenuItem(
        name = "Choco Milko",
        description = "Susu cokelat klasik yang lembut dan kaya rasa.",
        price = 12000,
        imageRes = R.drawable.choco_milko,
        category = "Choco Series"
    )

    private val chocoParadise = MenuItem(
        name = "Choco Paradise",
        description = "Minuman cokelat eksotis dengan sentuhan rasa buah-buahan tropis.",
        price = 17000,
        imageRes = R.drawable.choco_paradise,
        category = "Choco Series"
    )

    private val chocoSweetCaramel = MenuItem(
        name = "Choco Sweet Caramel",
        description = "Cokelat manis dengan lelehan saus karamel.",
        price = 16000,
        imageRes = R.drawable.csc,
        category = "Choco Series"
    )

    private val chocoBoo = MenuItem(
        name = "Choco Boo",
        description = "Minuman cokelat spesial dengan kejutan di dalamnya.",
        price = 18000,
        imageRes = R.drawable.choco_boo,
        category = "Choco Boo"
    )

    private val cimoryStrawberry = MenuItem(
        name = "Cimory Strawberry",
        description = "Yogurt drink rasa stroberi yang segar.",
        price = 14000,
        imageRes = R.drawable.cimory_strawberry,
        category = "Cimory Series"
    )

    private val cimoryBlueberry = MenuItem(
        name = "Cimory Blueberry",
        description = "Yogurt drink rasa bluberi yang nikmat.",
        price = 14000,
        imageRes = R.drawable.cimory_blueberry,
        category = "Cimory Series"
    )

    private val matchaLatte = MenuItem(
        name = "Matcha Latte",
        description = "Teh hijau matcha dengan susu steamed.",
        price = 15000,
        imageRes = R.drawable.matcha_latte,
        category = "Matcha Series"
    )

    private val matchaLatteBlend = MenuItem(
        name = "Matcha Latte Blend",
        description = "Matcha latte yang di-blend untuk tekstur lebih halus.",
        price = 16000,
        imageRes = R.drawable.matcha_latte_blend,
        category = "Matcha Series"
    )

    private val toppingOreo = MenuItem(
        name = "Topping Oreo",
        description = "Tambahan biskuit Oreo untuk minumanmu.",
        price = 3000,
        imageRes = R.drawable.oreo,
        category = "Topping"
    )

    private val toppingCaramel = MenuItem(
        name = "Topping Caramel",
        description = "Tambahan saus karamel untuk minumanmu.",
        price = 3000,
        imageRes = R.drawable.caramel,
        category = "Topping"
    )



    /**
     * Daftar semua item menu yang tersedia.
     * Ini akan menjadi sumber utama untuk ditampilkan di daftar menu lengkap.
     */
    val menuItems: List<MenuItem> = listOf(
        chocoMilk,
        chocoLatte,
        chocoBreeze,
        chocoMint,
        cocoaBlackCookies,
        chocoMilko,
        chocoParadise,
        chocoSweetCaramel,
        chocoBoo,
        cimoryStrawberry,
        cimoryBlueberry,
        matchaLatte,
        matchaLatteBlend,
        toppingOreo,
        toppingCaramel
    )

    /**
     * Daftar item yang direkomendasikan.
     * Ambil beberapa item dari daftar menu di atas untuk dijadikan rekomendasi.
     */
    val recommendations: List<MenuItem> = listOf(
        chocoLatte,     // Contoh: Choco Latte adalah item rekomendasi
        chocoMilk,      // Contoh: Choco Milk juga rekomendasi
        chocoBreeze,    // Item Tambahan
        chocoMint,
        cimoryStrawberry// Item Tambahan
    )
}