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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn

// Nanti, Repository akan di-inject melalui constructor menggunakan Hilt.
// Contoh: @HiltViewModel class MenuViewModel @Inject constructor(private val menuRepository: MenuRepository) : ViewModel()
class MenuViewModel(application: Application) : AndroidViewModel(application) {

    // --- BAGIAN REPOSITORY (PERUBAHAN UTAMA) ---
    // Inisialisasi Repository. Untuk sementara kita buat instance langsung.
    private val menuRepository = MenuRepository(DummyMenuDataSource())

    // Mengambil semua data dari Repository, bukan DummyData langsung.
    private val allMenuItems =
        (menuRepository.getAllMenuItems() + menuRepository.getRecommendedItems())
            .distinct()
            .associateBy { it.name }

    // --- Sisa kode sebagian besar tetap sama ---

    private val _quantities = mutableStateMapOf<String, Int>()
    val quantities: Map<String, Int> get() = _quantities

    private val _favorites = MutableStateFlow<Set<MenuItem>>(emptySet())
    val favorites = _favorites.asStateFlow()

    private val sharedPreferences = application.getSharedPreferences("polatta_favorite_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val FAVORITES_KEY = "favorite_menu_items"
    }

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        // Logika ini sudah benar, karena menggunakan allMenuItems yang sudah diperbarui.
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
        val menuItemFromRepo = allMenuItems[item.name] // Pastikan item ada di sumber data utama

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
}