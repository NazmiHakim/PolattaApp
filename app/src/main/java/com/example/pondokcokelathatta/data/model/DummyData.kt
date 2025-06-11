package com.pondokcokelathatta.data

import com.android.car.ui.toolbar.MenuItem
import com.pondokcokelathatta.model.MenuItem

// Dalam aplikasi nyata, data ini akan datang dari ViewModel yang terhubung ke Firebase.
object DummyData {

    val recommendations = listOf(
        MenuItem(1, "Choco Milk", price = 25, imageUrl = "https://i.imgur.com/7bEwN91.png"),
        MenuItem(2, "Choco Latte", price = 30, imageUrl = "https://i.imgur.com/L4R3B6c.png"),
        MenuItem(3, "Choco Breeze", price = 28, imageUrl = "https://i.imgur.com/qU3aF3a.png")
    )

    val menuItems = listOf(
        MenuItem(1, "Choco Milk", "Espresso with chocolate and steamed milk, often topped with whipped ....", 25, "https://i.imgur.com/7bEwN91.png"),
        MenuItem(2, "Choco Latte", "Espresso with a small amount of milk or foam.", 30, "https://i.imgur.com/L4R3B6c.png"),
        MenuItem(3, "Choco Breeze", "Espresso with a generous amount of steamed milk and a small layer of foam.", 28, "https://i.imgur.com/qU3aF3a.png"),
        MenuItem(4, "Café con Leche", "Similar to a latte, made with strong coffee and scalded milk.", 24, "https://i.imgur.com/9C3A0b9.png"),
        MenuItem(5, "Café Cubano", "Espresso with sugar, creating a sweet and strong coffee.", 20, "https://i.imgur.com/R8kX4S5.png")
    )

    val menuCategories = listOf("Choco Series", "Cimory Series", "Matcha", "Choco Boo", "Basreng")
}