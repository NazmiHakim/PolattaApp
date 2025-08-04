package com.example.pondokcokelathatta.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pondokcokelathatta.data.datasource.DummyMenuDataSource
import com.example.pondokcokelathatta.data.repository.MenuRepository
import com.example.pondokcokelathatta.model.MenuItem
import com.example.pondokcokelathatta.model.Order
import com.example.pondokcokelathatta.model.OrderStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// Sealed interface untuk merepresentasikan berbagai status UI
sealed interface UiState<out T> {
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val message: String) : UiState<Nothing>
    data object Loading : UiState<Nothing>
}

class MenuViewModel(application: Application) : AndroidViewModel(application) {

    private val menuRepository = MenuRepository(DummyMenuDataSource())
    private val sharedPreferences = application.getSharedPreferences("polatta_favorite_prefs", Context.MODE_PRIVATE)
    val authViewModel = AuthViewModel(application)


    companion object {
        private const val FAVORITES_KEY = "favorite_menu_items"
    }

    // --- State untuk UI ---
    private val _menuUiState = MutableStateFlow<UiState<List<MenuItem>>>(UiState.Loading)
    val menuUiState: StateFlow<UiState<List<MenuItem>>> = _menuUiState.asStateFlow()

    private val _recommendationsUiState = MutableStateFlow<UiState<List<MenuItem>>>(UiState.Loading)
    val recommendationsUiState: StateFlow<UiState<List<MenuItem>>> = _recommendationsUiState.asStateFlow()

    private val _favoritesState = MutableStateFlow<Set<String>>(emptySet())

    private val _quantities = MutableStateFlow<Map<String, Int>>(emptyMap())
    val quantities: StateFlow<Map<String, Int>> = _quantities.asStateFlow()

    private val _ongoingOrders = MutableStateFlow<List<Order>>(emptyList())
    val ongoingOrders: StateFlow<List<Order>> = _ongoingOrders.asStateFlow()

    private val _historyOrders = MutableStateFlow<List<Order>>(emptyList())
    val historyOrders: StateFlow<List<Order>> = _historyOrders.asStateFlow()

    // --- State Turunan (Derived State) ---
    private val allItemsFlow: Flow<Map<String, MenuItem>> = menuRepository.getAllItemsCombined()
        .map { list -> list.associateBy { it.name } }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyMap())

    // State untuk daftar item favorit
    val favoritesUiState: StateFlow<UiState<List<MenuItem>>> = combine(
        allItemsFlow,
        _favoritesState
    ) { allItems, favoriteNames ->
        val favoriteItems = favoriteNames.mapNotNull { allItems[it] }
        // --- PERBAIKAN DI SINI ---
        // Memberi tahu compiler bahwa tipe keluarannya adalah UiState<List<MenuItem>>
        UiState.Success(favoriteItems) as UiState<List<MenuItem>>
    }.catch {
        // Blok catch sekarang dapat dengan aman memancarkan UiState.Error
        emit(UiState.Error("Gagal memuat favorit."))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)


    // State untuk item yang dipesan (kuantitas > 0)
    val orderedItems: StateFlow<Map<MenuItem, Int>> = combine(
        allItemsFlow,
        quantities
    ) { allItems, quants ->
        quants.filter { it.value > 0 }.mapNotNull { (name, quantity) ->
            allItems[name]?.let { item -> item to quantity }
        }.toMap()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())


    val totalQuantity: StateFlow<Int> = quantities.map { it.values.sum() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val totalPrice: StateFlow<Int> = orderedItems.map { items ->
        items.entries.sumOf { (item, quantity) -> item.price * quantity }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)


    init {
        loadAllData()
        loadFavoritesFromPrefs()
    }

    fun refreshData() {
        loadAllData()
    }

    private fun loadAllData() {
        viewModelScope.launch {
            _menuUiState.value = UiState.Loading
            _recommendationsUiState.value = UiState.Loading
            delay(1000) // Simulasi loading

            menuRepository.getAllMenuItems()
                .catch { e -> _menuUiState.value = UiState.Error("Gagal memuat menu: ${e.message}") }
                .collect { _menuUiState.value = UiState.Success(it) }

            menuRepository.getRecommendedItems()
                .catch { e ->
                    _recommendationsUiState.value =
                        UiState.Error("Gagal memuat rekomendasi: ${e.message}")
                }
                .collect { _recommendationsUiState.value = UiState.Success(it) }
        }
    }

    private fun loadFavoritesFromPrefs() {
        val favoriteNames = sharedPreferences.getStringSet(FAVORITES_KEY, emptySet()) ?: emptySet()
        _favoritesState.value = favoriteNames
    }

    private fun saveFavoritesToPrefs() {
        with(sharedPreferences.edit()) {
            putStringSet(FAVORITES_KEY, _favoritesState.value)
            apply()
        }
    }

    fun increaseQuantity(item: MenuItem) {
        _quantities.update { current ->
            val newQuantity = (current[item.name] ?: 0) + 1
            current + (item.name to newQuantity)
        }
    }

    fun decreaseQuantity(item: MenuItem) {
        _quantities.update { current ->
            val newQuantity = (current[item.name] ?: 0) - 1
            if (newQuantity > 0) {
                current + (item.name to newQuantity)
            } else {
                current - item.name
            }
        }
    }

    fun toggleFavorite(item: MenuItem) {
        _favoritesState.update { currentFavorites ->
            if (item.name in currentFavorites) {
                currentFavorites - item.name
            } else {
                currentFavorites + item.name
            }
        }
        saveFavoritesToPrefs()
    }

    fun placeOrder() {
        if (orderedItems.value.isNotEmpty()) {
            val newOrder = Order(
                items = orderedItems.value,
                totalPrice = totalPrice.value
            )
            _ongoingOrders.update { it + newOrder }
            _quantities.value = emptyMap()
        }
    }

    fun completeOrder(order: Order) {
        _ongoingOrders.update { currentOrders -> currentOrders.filterNot { it.id == order.id } }
        _historyOrders.update { currentHistory -> currentHistory + order.copy(status = OrderStatus.COMPLETED) }
    }

    fun cancelOrder(order: Order) {
        _ongoingOrders.update { currentOrders -> currentOrders.filterNot { it.id == order.id } }
    }
}