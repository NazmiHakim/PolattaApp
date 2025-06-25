package com.example.pondokcokelathatta.data.datasource

import com.example.pondokcokelathatta.data.model.DummyData
import com.example.pondokcokelathatta.model.MenuItem

/**
 * Implementasi sumber data yang mengambil menu dari objek DummyData.
 * Ini adalah langkah sementara sebelum beralih ke sumber data nyata.
 */
class DummyMenuDataSource : MenuDataSource {
    override fun getAllMenus(): List<MenuItem> {
        return DummyData.menuItems
    }

    override fun getRecommendations(): List<MenuItem> {
        return DummyData.recommendations
    }
}