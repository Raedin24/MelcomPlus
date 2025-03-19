//FavoritesViewModel.kt

package com.example.melcomplus.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.melcomplus.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FavoritesViewModel : ViewModel() {
    private val _favorites = MutableStateFlow<List<Product>>(emptyList())
    val favorites: StateFlow<List<Product>> get() = _favorites.asStateFlow()

    fun addToFavorites(product: Product) {
        _favorites.value += product
    }

    fun removeFromFavorites(product: Product) {
        _favorites.value -= product
    }

    val favoriteItemCount: StateFlow<Int> = _favorites
        .map { it.size }  // Transform the list into its size
        .stateIn(viewModelScope, SharingStarted.Lazily, 0) // Ensure state is managed properly
}
