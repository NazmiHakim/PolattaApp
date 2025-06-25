package com.example.pondokcokelathatta.data.repository

import com.example.pondokcokelathatta.data.datasource.MenuDataSource
import com.example.pondokcokelathatta.model.MenuItem

/**
 * Repository sebagai Single Source of Truth (SSOT) untuk semua data terkait menu.
 * Ia bertanggung jawab untuk mengambil data dari DataSource.
 */
class MenuRepository(
    // Nanti, DataSource ini akan disuntikkan (injected) menggunakan Hilt
    private val dataSource: MenuDataSource
) {
    fun getAllMenuItems(): List<MenuItem> {
        return dataSource.getAllMenus()
    }

    fun getRecommendedItems(): List<MenuItem> {
        return dataSource.getRecommendations()
    }
}