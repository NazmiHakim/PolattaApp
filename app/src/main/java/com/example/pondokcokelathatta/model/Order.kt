package com.example.pondokcokelathatta.model

import java.util.UUID

// Data class untuk merepresentasikan pesanan
data class Order(
    val id: String = UUID.randomUUID().toString(), // ID unik untuk setiap pesanan
    val items: Map<MenuItem, Int>, // Daftar item dan kuantitasnya
    val totalPrice: Int,
    val status: OrderStatus = OrderStatus.ONGOING
)

// Enum untuk status pesanan
enum class OrderStatus {
    ONGOING,
    COMPLETED
}