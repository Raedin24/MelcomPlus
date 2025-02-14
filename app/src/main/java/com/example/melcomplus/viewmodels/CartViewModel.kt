//CartViewModel.kt
package com.example.melcomplus.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.melcomplus.models.Product
import com.example.melcomplus.models.CartItem

class CartViewModel : ViewModel() {
    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> get() = _cartItems

    var totalCost by mutableDoubleStateOf(0.0)
        private set

    fun addToCart(product: Product) {
        val existingItem = _cartItems.find {it.product.name == product.name}
        if (existingItem != null) {
            existingItem.quantity += 1
        } else {
            _cartItems.add(CartItem(product, 1))
        }
        updateTotalCost()
    }

    fun increaseQuantity(cartItem: CartItem) {
        cartItem.quantity += 1
        updateTotalCost()
    }

    fun decreaseQuantity(cartItem: CartItem) {
        if (cartItem.quantity > 1) {
            cartItem.quantity -= 1
        } else {
            _cartItems.remove(cartItem)
        }
        updateTotalCost()
    }
    fun removeFromCart(cartItem: CartItem) {
        _cartItems.remove(cartItem)
        updateTotalCost()
    }

    fun clearCart() {
        _cartItems.clear()
        totalCost = 0.0
    }

    private fun updateTotalCost() {
        totalCost = _cartItems.sumOf { it.product.price * it.quantity }
    }
}
