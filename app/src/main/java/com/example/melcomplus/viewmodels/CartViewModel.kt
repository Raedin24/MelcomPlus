//CartViewModel.kt

package com.example.melcomplus.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.melcomplus.models.CartItem
import com.example.melcomplus.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.RoundingMode

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> get() = _cartItems.asStateFlow()

    private val _totalCost = MutableStateFlow(0.0)
    val totalCost: StateFlow<Double> get() = _totalCost.asStateFlow()

    // New: cartItemCount flow
    private val _cartItemCount = MutableStateFlow(0)
    val cartItemCount: StateFlow<Int> get() = _cartItemCount.asStateFlow()

    private val _placedOrders = MutableStateFlow<List<Product>>(emptyList())
    val placedOrders: StateFlow<List<Product>> get() = _placedOrders.asStateFlow()

    fun placeOrder(products: List<Product>) {
        viewModelScope.launch {
            val uniqueProducts = LinkedHashSet<Product>(_placedOrders.value)
            products.reversed().forEach { product ->
                uniqueProducts.remove(product)
                uniqueProducts.add(product)
            }
            _placedOrders.value = uniqueProducts.toList().reversed()
            clearCart()
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            val existingItem = _cartItems.value.find { it.product.name == product.name }
            if (existingItem != null) {
                _cartItems.value = _cartItems.value.map {
                    if (it.product.name == product.name) it.copy(quantity = it.quantity + 1)
                    else it
                }
            } else {
                _cartItems.value += CartItem(product, 1)
            }
            updateTotalCost()
            updateCartItemCount()
        }
    }

    fun increaseQuantity(cartItem: CartItem) {
        viewModelScope.launch {
            _cartItems.value = _cartItems.value.map {
                if (it.product.name == cartItem.product.name) it.copy(quantity = it.quantity + 1)
                else it
            }
            updateTotalCost()
            updateCartItemCount()
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
                _cartItems.value = _cartItems.value.filter { it.product.name != cartItem.product.name }
            }
            updateTotalCost()
            updateCartItemCount()
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        viewModelScope.launch {
            _cartItems.value = _cartItems.value.filter { it.product.name != cartItem.product.name }
            updateTotalCost()
            updateCartItemCount()
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            _cartItems.value = emptyList()
            _totalCost.value = 0.0
            _cartItemCount.value = 0
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

    // New: Function to update cart item count
    private fun updateCartItemCount() {
        viewModelScope.launch {
            _cartItemCount.value = _cartItems.value.sumOf { it.quantity }
        }
    }
}
