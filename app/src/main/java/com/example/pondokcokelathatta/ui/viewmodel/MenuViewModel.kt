package com.example.pondokcokelathatta.ui.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import com.example.pondokcokelathatta.model.MenuItem

class MenuViewModel : ViewModel() {
    // Gunakan Map untuk menyimpan kuantitas setiap item menu berdasarkan namanya (atau ID unik jika ada)
    // _quantities adalah private untuk memastikan hanya ViewModel yang bisa mengubahnya secara langsung.
    private val _quantities = mutableStateMapOf<String, Int>()

    // Expose map sebagai State yang tidak bisa diubah dari luar ViewModel
    val quantities: Map<String, Int>
        get() = _quantities

    // Fungsi untuk menambah kuantitas item
    fun increaseQuantity(item: MenuItem) {
        _quantities[item.name] = (_quantities[item.name] ?: 0) + 1
    }

    // Fungsi untuk mengurangi kuantitas item
    fun decreaseQuantity(item: MenuItem) {
        val currentQuantity = _quantities[item.name] ?: 0
        if (currentQuantity > 0) {
            _quantities[item.name] = currentQuantity - 1
        }
    }
}