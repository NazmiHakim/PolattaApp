package com.example.pondokcokelathatta.data.model

import com.example.pondokcokelathatta.model.MenuItem

/**
 * Dalam aplikasi nyata, data ini akan datang dari ViewModel yang terhubung ke Firebase.
 */
object DummyData {

    // Daftar item yang direkomendasikan, disesuaikan dengan model MenuItem.
    val recommendations = listOf(
        MenuItem(
            name = "Choco Milk",
            description = "Espresso dengan cokelat dan susu steamed, seringkali dengan topping whipped cream.",
            price = 25
        ),
        MenuItem(
            name = "Choco Latte",
            description = "Espresso dengan sedikit susu atau busa.",
            price = 30
        ),
        MenuItem(
            name = "Choco Breeze",
            description = "Espresso dengan banyak susu steamed dan lapisan busa tipis.",
            price = 28
        )
    )

    // Daftar semua item menu yang tersedia.
    val menuItems = listOf(
        MenuItem(
            name = "Choco Milk",
            description = "Espresso dengan cokelat dan susu steamed, seringkali dengan topping whipped ....",
            price = 25
        ),
        MenuItem(
            name = "Choco Latte",
            description = "Espresso dengan sedikit susu atau busa.",
            price = 30
        ),
        MenuItem(
            name = "Choco Breeze",
            description = "Espresso dengan banyak susu steamed dan lapisan busa tipis.",
            price = 28
        ),
        MenuItem(
            name = "Café con Leche",
            description = "Mirip dengan latte, dibuat dengan kopi kental dan susu panas.",
            price = 24
        ),
        MenuItem(
            name = "Café Cubano",
            description = "Espresso dengan gula, menghasilkan kopi yang manis dan kuat.",
            price = 20
        )
    )

    // Daftar kategori menu.
    val menuCategories = listOf("Choco Series", "Cimory Series", "Matcha", "Choco Boo", "Basreng")
}