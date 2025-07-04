package com.example.pondokcokelathatta.data.repository

import com.example.pondokcokelathatta.data.datasource.MenuDataSource
import com.example.pondokcokelathatta.model.MenuItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

/**
 * Repository sebagai Single Source of Truth (SSOT) untuk semua data terkait menu.
 * Ia bertanggung jawab untuk mengambil data dari DataSource dan bisa menambahkan
 * logika bisnis seperti caching atau menggabungkan data dari beberapa sumber.
 */
class MenuRepository(
    private val dataSource: MenuDataSource
) {
    fun getAllMenuItems(): Flow<List<MenuItem>> {
        return dataSource.getAllMenus()
    }

    fun getRecommendedItems(): Flow<List<MenuItem>> {
        return dataSource.getRecommendations()
    }

    /**
     * Menggabungkan semua menu dan rekomendasi menjadi satu list unik.
     * Berguna untuk pencarian dan detail item.
     */
    fun getAllItemsCombined(): Flow<List<MenuItem>> {
        return combine(getAllMenuItems(), getRecommendedItems()) { menu, recommendations ->
            (menu + recommendations).distinctBy { it.name }
        }
    }
}