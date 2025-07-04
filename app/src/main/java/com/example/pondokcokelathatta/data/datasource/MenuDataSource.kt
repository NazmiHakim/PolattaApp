package com.example.pondokcokelathatta.data.datasource

import com.example.pondokcokelathatta.model.MenuItem
import kotlinx.coroutines.flow.Flow

/**
 * Antarmuka (Interface) untuk sumber data menu.
 * Ini mendefinisikan *apa* yang bisa dilakukan (mengambil menu),
 * bukan *bagaimana* caranya. Ini memungkinkan kita menukar implementasi
 * (misal dari DummyData ke Retrofit/Room) tanpa mengubah ViewModel.
 */
interface MenuDataSource {
    /**
     * Mengambil semua item menu.
     * Menggunakan Flow agar bisa bersifat reaktif terhadap perubahan data.
     */
    fun getAllMenus(): Flow<List<MenuItem>>

    /**
     * Mengambil item menu yang direkomendasikan.
     */
    fun getRecommendations(): Flow<List<MenuItem>>
}