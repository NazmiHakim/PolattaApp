package com.example.pondokcokelathatta.model

data class User(
    val uid: String = "",
    val email: String = "",
    val name: String = "",
    val role: String = "customer" // Default role is 'customer'
)