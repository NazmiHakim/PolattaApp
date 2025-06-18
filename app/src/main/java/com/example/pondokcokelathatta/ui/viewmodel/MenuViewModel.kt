package com.example.pondokcokelathatta.ui.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import com.example.pondokcokelathatta.model.MenuItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MenuViewModel : ViewModel() {
    private val _quantities = mutableStateMapOf<String, Int>()
    val quantities: Map<String, Int> get() = _quantities

    private val _favorites = MutableStateFlow<Set<MenuItem>>(emptySet())
    val favorites = _favorites.asStateFlow()

    fun increaseQuantity(item: MenuItem) {
        _quantities[item.name] = (_quantities[item.name] ?: 0) + 1
    }

    fun decreaseQuantity(item: MenuItem) {
        val currentQuantity = _quantities[item.name] ?: 0
        if (currentQuantity > 0) {
            _quantities[item.name] = currentQuantity - 1
        }
    }

    fun toggleFavorite(item: MenuItem) {
        val currentFavorites = _favorites.value.toMutableSet()
        if (currentFavorites.contains(item)) {
            currentFavorites.remove(item)
        } else {
            currentFavorites.add(item)
        }
        _favorites.value = currentFavorites
    }
}