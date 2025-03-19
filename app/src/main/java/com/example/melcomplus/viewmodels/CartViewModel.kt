////CartViewModel.kt

package com.example.melcomplus.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.RoundingMode
import com.example.melcomplus.models.Product
import com.example.melcomplus.models.CartItem

class CartViewModel : ViewModel() {
    // Use MutableStateFlow for cartItems to make it observable
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> get() = _cartItems.asStateFlow()

    // Use MutableStateFlow for totalCost to make it observable
    private val _totalCost = MutableStateFlow(0.0)
    val totalCost: StateFlow<Double> get() = _totalCost.asStateFlow()

    // List of products from placed orders
    private val _placedOrders = mutableStateListOf<Product>()
    val placedOrders: List<Product> get() = _placedOrders

    // Function to add products to the placed orders list
    fun placeOrder(products: List<Product>) {
        val uniqueProducts = LinkedHashSet<Product>(_placedOrders) // Preserve order while ensuring uniqueness

        // Move existing products to the front and add new ones
        products.reversed().forEach { product ->
            uniqueProducts.remove(product) // Remove if it already exists
            uniqueProducts.add(product) // Add it back to ensure it's at the front
        }

        _placedOrders.clear()
        _placedOrders.addAll(uniqueProducts.toList().reversed()) // Maintain recent items at the front

        clearCart() // Clear cart after placing order
    }

    // List of favorite products
    private val _favorites = MutableStateFlow<List<Product>>(emptyList())
    val favorites: StateFlow<List<Product>> get() = _favorites

    // Add a product to favorites
    fun addToFavorites(product: Product) {
        _favorites.value = _favorites.value + product
    }

    // Remove a product from favorites
    fun removeFromFavorites(product: Product) {
        _favorites.value = _favorites.value - product
    }

    // Check if a product is in favorites
    // Check if a product is in favorites
    fun isFavorite(product: Product): Boolean {
        return _favorites.value.contains(product)
    }



    fun addToCart(product: Product) {
        viewModelScope.launch {
            val existingItem = _cartItems.value.find { it.product.name == product.name }
            if (existingItem != null) {
                // If the product is already in the cart, increase its quantity
                _cartItems.value = _cartItems.value.map {
                    if (it.product.name == product.name) it.copy(quantity = it.quantity + 1)
                    else it
                }
            } else {
                // If the product is not in the cart, add it with quantity 1
                _cartItems.value = _cartItems.value + CartItem(product, 1)
            }
            updateTotalCost()
        }
    }

    fun increaseQuantity(cartItem: CartItem) {
        viewModelScope.launch {
            _cartItems.value = _cartItems.value.map {
                if (it.product.name == cartItem.product.name) it.copy(quantity = it.quantity + 1)
                else it
            }
            updateTotalCost()
        }
    }

    fun decreaseQuantity(cartItem: CartItem) {
        viewModelScope.launch {
            if (cartItem.quantity > 1) {
                _cartItems.value = _cartItems.value.map {
                    if (it.product.name == cartItem.product.name) it.copy(quantity = it.quantity - 1)
                    else it
                }
            } else {
                // Remove item from cart if quantity is 1
                _cartItems.value = _cartItems.value.filter { it.product.name != cartItem.product.name }
            }
            updateTotalCost()
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        viewModelScope.launch {
            _cartItems.value = _cartItems.value.filter { it.product.name != cartItem.product.name }
            updateTotalCost()
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            _cartItems.value = emptyList()
            _totalCost.value = 0.0
        }
    }

    private fun updateTotalCost() {
        viewModelScope.launch {
            _totalCost.value = _cartItems.value.sumOf { it.product.price * it.quantity }
                .toBigDecimal()
                .setScale(2, RoundingMode.HALF_EVEN)
                .toDouble()
        }
    }
}