//CartScreen.kt
package com.example.melcomplus.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.melcomplus.viewmodels.CartViewModel
import com.example.melcomplus.models.CartItem

@Composable
fun CartScreen(cartViewModel: CartViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Cart", style = MaterialTheme.typography.headlineSmall)

        if (cartViewModel.cartItems.isEmpty()) {
            Text("Cart is empty", style = MaterialTheme.typography.bodyLarge)
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(cartViewModel.cartItems) { cartItem ->
                    CartItemRow(cartItem, cartViewModel)
                }
            }

            Text(
                text = "Total: \$${cartViewModel.totalCost}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Button(
                onClick = { cartViewModel.clearCart() },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
            ) {
                Text("Clear Cart")
            }
        }
    }
}

@Composable
fun CartItemRow(cartItem: CartItem, cartViewModel: CartViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(cartItem.product.name, modifier = Modifier.weight(1f))
            Text(text = "\$${cartItem.product.price}")

            Row {
                IconButton(onClick = { cartViewModel.decreaseQuantity(cartItem) }) {
                    Text("-")
                }
                Text(text = "${cartItem.quantity}", modifier = Modifier.padding(horizontal = 8.dp))
                IconButton(onClick = { cartViewModel.increaseQuantity(cartItem) }) {
                    Text("+")
                }
            }

            IconButton(onClick = { cartViewModel.removeFromCart(cartItem) }) {
                Text("🗑")
            }
        }
    }
}
