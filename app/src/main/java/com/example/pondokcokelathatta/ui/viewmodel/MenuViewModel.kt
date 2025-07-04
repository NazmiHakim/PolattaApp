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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Sealed interface untuk merepresentasikan berbagai status UI
sealed interface UiState<out T> {
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val message: String) : UiState<Nothing>
    data object Loading : UiState<Nothing>
}

class MenuViewModel(application: Application) : AndroidViewModel(application) {

    private val menuRepository = MenuRepository(DummyMenuDataSource())

    // State untuk daftar menu
    private val _menuUiState = MutableStateFlow<UiState<List<MenuItem>>>(UiState.Loading)
    val menuUiState: StateFlow<UiState<List<MenuItem>>> = _menuUiState.asStateFlow()

    // State untuk daftar rekomendasi
    private val _recommendationsUiState = MutableStateFlow<UiState<List<MenuItem>>>(UiState.Loading)
    val recommendationsUiState: StateFlow<UiState<List<MenuItem>>> = _recommendationsUiState.asStateFlow()

    private val _quantities = mutableStateMapOf<String, Int>()
    val quantities: Map<String, Int> get() = _quantities

    // Menggunakan UiState untuk daftar favorit
    private val _favoritesUiState = MutableStateFlow<UiState<Set<MenuItem>>>(UiState.Loading)
    val favoritesUiState: StateFlow<UiState<Set<MenuItem>>> = _favoritesUiState.asStateFlow()

    private val allMenuItems =
        (menuRepository.getAllMenuItems() + menuRepository.getRecommendedItems())
            .distinct()
            .associateBy { it.name }

    private val _ongoingOrders = MutableStateFlow<List<Order>>(emptyList())
    val ongoingOrders = _ongoingOrders.asStateFlow()

    private val _historyOrders = MutableStateFlow<List<Order>>(emptyList())
    val historyOrders = _historyOrders.asStateFlow()

    private val sharedPreferences = application.getSharedPreferences("polatta_favorite_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val FAVORITES_KEY = "favorite_menu_items"
    }

    init {
        loadAllData()
    }

    fun loadAllData() {
        viewModelScope.launch {
            // Set semua state ke Loading
            _menuUiState.value = UiState.Loading
            _recommendationsUiState.value = UiState.Loading
            _favoritesUiState.value = UiState.Loading

            try {
                // Simulasi delay jaringan
                delay(1500)

                // Muat data menu
                _menuUiState.value = UiState.Success(menuRepository.getAllMenuItems())

                // Muat data rekomendasi
                _recommendationsUiState.value = UiState.Success(menuRepository.getRecommendedItems())

                // Muat data favorit dari SharedPreferences
                val favoriteNames = sharedPreferences.getStringSet(FAVORITES_KEY, emptySet()) ?: emptySet()
                val favoriteItems = allMenuItems.values.filter { it.name in favoriteNames }.toSet()
                _favoritesUiState.value = UiState.Success(favoriteItems)

            } catch (e: Exception) {
                // Jika terjadi error
                val errorMessage = "Gagal memuat data. Silakan coba lagi."
                _menuUiState.value = UiState.Error(errorMessage)
                _recommendationsUiState.value = UiState.Error(errorMessage)
                _favoritesUiState.value = UiState.Error(errorMessage)
            }
        }
    }


    @SuppressLint("UseKtx")
    private fun saveFavorites() {
        (_favoritesUiState.value as? UiState.Success)?.data?.let {
            val favoriteNames = it.map { item -> item.name }.toSet()
            with(sharedPreferences.edit()) {
                putStringSet(FAVORITES_KEY, favoriteNames)
                apply()
            }
        }
    }


    fun increaseQuantity(item: MenuItem) {
        _quantities[item.name] = (_quantities[item.name] ?: 0) + 1
        // Analytics: Lacak penambahan item
        // trackEvent("add_to_cart", mapOf("item_name" to item.name, "quantity" to (_quantities[item.name])))
    }

    fun decreaseQuantity(item: MenuItem) {
        val currentQuantity = _quantities[item.name] ?: 0
        if (currentQuantity > 0) {
            _quantities[item.name] = currentQuantity - 1
            // Analytics: Lacak pengurangan item
            // trackEvent("remove_from_cart", mapOf("item_name" to item.name, "quantity" to (_quantities[item.name])))
        }
    }

    fun toggleFavorite(item: MenuItem) {
        if (_favoritesUiState.value is UiState.Success) {
            val currentFavorites = (_favoritesUiState.value as UiState.Success).data.toMutableSet()
            val menuItemFromRepo = allMenuItems[item.name]

            if (menuItemFromRepo != null) {
                if (currentFavorites.any { it.name == menuItemFromRepo.name }) {
                    currentFavorites.removeAll { it.name == menuItemFromRepo.name }
                    // Analytics: Lacak item dihapus dari favorit
                    // trackEvent("remove_from_favorites", mapOf("item_name" to item.name))
                } else {
                    currentFavorites.add(menuItemFromRepo)
                    // Analytics: Lacak item ditambah ke favorit
                    // trackEvent("add_to_favorites", mapOf("item_name" to item.name))
                }
                _favoritesUiState.value = UiState.Success(currentFavorites)
                saveFavorites()
            }
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

    fun placeOrder() {
        val itemsToOrder = orderedItems.value
        if (itemsToOrder.isNotEmpty()) {
            val newOrder = Order(
                items = itemsToOrder,
                totalPrice = totalPrice.value
            )
            _ongoingOrders.update { currentOrders -> currentOrders + newOrder }
            _quantities.clear()
            // Analytics: Lacak pesanan berhasil dibuat
            // trackEvent("place_order_success", mapOf("total_price" to totalPrice.value, "item_count" to itemsToOrder.size))
        }
    }

    fun completeOrder(order: Order) {
        _ongoingOrders.update { currentOrders -> currentOrders.filterNot { it.id == order.id } }
        _historyOrders.update { currentHistory -> currentHistory + order.copy(status = com.example.pondokcokelathatta.model.OrderStatus.COMPLETED) }
    }
}