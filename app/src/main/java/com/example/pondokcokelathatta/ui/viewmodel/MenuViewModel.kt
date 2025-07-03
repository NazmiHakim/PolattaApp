package com.example.pondokcokelathatta.ui.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pondokcokelathatta.data.datasource.DummyMenuDataSource
import com.example.pondokcokelathatta.data.repository.MenuRepository
import com.example.pondokcokelathatta.model.MenuItem
import com.example.pondokcokelathatta.model.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MenuViewModel(application: Application) : AndroidViewModel(application) {

    private val menuRepository = MenuRepository(DummyMenuDataSource())
    private val allMenuItems =
        (menuRepository.getAllMenuItems() + menuRepository.getRecommendedItems())
            .distinct()
            .associateBy { it.name }

    private val _quantities = mutableStateMapOf<String, Int>()
    val quantities: Map<String, Int> get() = _quantities

    private val _favorites = MutableStateFlow<Set<MenuItem>>(emptySet())
    val favorites = _favorites.asStateFlow()

    // --- PERUBAHAN UTAMA: State untuk Pesanan ---
    private val _ongoingOrders = MutableStateFlow<List<Order>>(emptyList())
    val ongoingOrders = _ongoingOrders.asStateFlow()

    private val _historyOrders = MutableStateFlow<List<Order>>(emptyList())
    val historyOrders = _historyOrders.asStateFlow()
    // -------------------------------------------

    private val sharedPreferences = application.getSharedPreferences("polatta_favorite_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val FAVORITES_KEY = "favorite_menu_items"
    }

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        val favoriteNames = sharedPreferences.getStringSet(FAVORITES_KEY, emptySet()) ?: emptySet()
        _favorites.value = allMenuItems.values.filter { it.name in favoriteNames }.toSet()
    }

    @SuppressLint("UseKtx")
    private fun saveFavorites() {
        val favoriteNames = _favorites.value.map { it.name }.toSet()
        with(sharedPreferences.edit()) {
            putStringSet(FAVORITES_KEY, favoriteNames)
            apply()
        }
    }

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
        val menuItemFromRepo = allMenuItems[item.name]

        if (menuItemFromRepo != null) {
            if (currentFavorites.any { it.name == menuItemFromRepo.name }) {
                currentFavorites.removeAll { it.name == menuItemFromRepo.name }
            } else {
                currentFavorites.add(menuItemFromRepo)
            }
            _favorites.value = currentFavorites
            saveFavorites()
        }
    }

    val totalQuantity = snapshotFlow { _quantities.values.sum() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    val totalPrice = snapshotFlow {
        _quantities.map { (itemName, quantity) ->
            (allMenuItems[itemName]?.price ?: 0) * quantity
        }.sum()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    val orderedItems = snapshotFlow {
        _quantities.filter { it.value > 0 }
            .mapNotNull { (itemName, quantity) ->
                allMenuItems[itemName]?.let { menuItem ->
                    menuItem to quantity
                }
            }
            .toMap()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyMap()
    )

    // --- FUNGSI BARU UNTUK MANAJEMEN PESANAN ---
    fun placeOrder() {
        val itemsToOrder = orderedItems.value
        if (itemsToOrder.isNotEmpty()) {
            val newOrder = Order(
                items = itemsToOrder,
                totalPrice = totalPrice.value
            )
            _ongoingOrders.update { currentOrders -> currentOrders + newOrder }
            // Hapus item dari keranjang setelah pesanan dibuat
            _quantities.clear()
        }
    }

    fun completeOrder(order: Order) {
        // Pindahkan dari ongoing ke history
        _ongoingOrders.update { currentOrders -> currentOrders.filterNot { it.id == order.id } }
        _historyOrders.update { currentHistory -> currentHistory + order.copy(status = com.example.pondokcokelathatta.model.OrderStatus.COMPLETED) }
    }
    // ------------------------------------------
}