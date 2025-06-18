package com.example.pondokcokelathatta.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pondokcokelathatta.data.model.DummyData
import com.example.pondokcokelathatta.model.MenuItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn

// Ubah dari ViewModel menjadi AndroidViewModel
class MenuViewModel(application: Application) : AndroidViewModel(application) {
    private val _quantities = mutableStateMapOf<String, Int>()
    val quantities: Map<String, Int> get() = _quantities

    private val _favorites = MutableStateFlow<Set<MenuItem>>(emptySet())
    val favorites = _favorites.asStateFlow()

    // Inisialisasi SharedPreferences
    private val sharedPreferences = application.getSharedPreferences("polatta_favorite_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val FAVORITES_KEY = "favorite_menu_items"
    }

    // Blok init akan dipanggil saat ViewModel pertama kali dibuat
    init {
        loadFavorites()
    }

    /**
     * Memuat daftar nama item favorit dari SharedPreferences,
     * kemudian mencocokkannya dengan data dari DummyData untuk mendapatkan objek MenuItem.
     */
    private fun loadFavorites() {
        // Ambil semua item menu yang ada dan pastikan tidak ada duplikat
        val allMenuItems = (DummyData.menuItems + DummyData.recommendations).distinct()
        // Baca data nama favorit dari SharedPreferences, jika tidak ada, kembalikan set kosong
        val favoriteNames = sharedPreferences.getStringSet(FAVORITES_KEY, emptySet()) ?: emptySet()
        // Filter daftar semua menu untuk mendapatkan objek MenuItem yang namanya ada di daftar favorit
        _favorites.value = allMenuItems.filter { it.name in favoriteNames }.toSet()
    }

    /**
     * Menyimpan daftar nama item favorit saat ini ke dalam SharedPreferences.
     */
    private fun saveFavorites() {
        // Ambil nama dari setiap item di state _favorites
        val favoriteNames = _favorites.value.map { it.name }.toSet()
        // Simpan set nama tersebut ke SharedPreferences
        with(sharedPreferences.edit()) {
            putStringSet(FAVORITES_KEY, favoriteNames)
            apply() // apply() berjalan di background, lebih efisien daripada commit()
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

    /**
     * Mengubah status favorit sebuah item dan langsung menyimpannya ke SharedPreferences.
     */
    fun toggleFavorite(item: MenuItem) {
        val currentFavorites = _favorites.value.toMutableSet()
        if (currentFavorites.contains(item)) {
            currentFavorites.remove(item)
        } else {
            currentFavorites.add(item)
        }
        _favorites.value = currentFavorites

        // Panggil fungsi save setiap kali ada perubahan pada daftar favorit
        saveFavorites()
    }

    // Kumpulkan semua item menu unik dari DummyData
    private val allMenuItems = (DummyData.menuItems + DummyData.recommendations).distinct().associateBy { it.name }

    // Flow untuk memantau total kuantitas secara real-time
    val totalQuantity = snapshotFlow { _quantities.values.sum() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    // Flow untuk memantau total harga secara real-time
    val totalPrice = snapshotFlow {
        _quantities.map { (itemName, quantity) ->
            (allMenuItems[itemName]?.price ?: 0) * quantity
        }.sum()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    // Flow untuk mendapatkan daftar item yang sedang dipesan (kuantitas > 0)
    val orderedItems = snapshotFlow {
        _quantities.filter { it.value > 0 }
            .mapNotNull { (itemName, quantity) ->
                // Gunakan mapNotNull dan safe call (?.) untuk memastikan hanya item yang valid yang diproses
                allMenuItems[itemName]?.let { menuItem ->
                    menuItem to quantity // Membuat Pair<MenuItem, Int>
                }
            }
            .toMap() // Mengonversi List<Pair<MenuItem, Int>> menjadi Map<MenuItem, Int>
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyMap()
    )

}