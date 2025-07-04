package com.example.pondokcokelathatta.data.datasource

import com.example.pondokcokelathatta.data.model.DummyData
import com.example.pondokcokelathatta.model.MenuItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Implementasi sumber data yang mengambil menu dari objek DummyData.
 * Menggunakan flowOf untuk mengubah list statis menjadi Flow,
 * agar sesuai dengan kontrak MenuDataSource.
 */
class DummyMenuDataSource : MenuDataSource {
    override fun getAllMenus(): Flow<List<MenuItem>> {
        return flowOf(DummyData.menuItems)
    }

    override fun getRecommendations(): Flow<List<MenuItem>> {
        return flowOf(DummyData.recommendations)
    }
}