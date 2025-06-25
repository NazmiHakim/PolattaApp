package com.example.pondokcokelathatta.data.datasource

import com.example.pondokcokelathatta.model.MenuItem

/**
 * Antarmuka (Interface) untuk sumber data menu.
 * Ini mendefinisikan *apa* yang bisa dilakukan (mengambil menu),
 * bukan *bagaimana* caranya.
 */
interface MenuDataSource {
    fun getAllMenus(): List<MenuItem>
    fun getRecommendations(): List<MenuItem>
}