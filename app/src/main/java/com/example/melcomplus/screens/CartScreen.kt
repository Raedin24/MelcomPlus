//CartScreen.kt
package com.example.melcomplus.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.melcomplus.models.CartItem
import com.example.melcomplus.viewmodels.CartViewModel

//@Composable
//fun CartScreen(cartViewModel: CartViewModel) {
//    // Collect cartItems state from CartViewModel
//    val cartItems by cartViewModel.cartItems.collectAsState()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text("Cart", style = MaterialTheme.typography.headlineSmall)
//
//        if (cartItems.isEmpty()) {
//            Text("Cart is empty", style = MaterialTheme.typography.bodyLarge)
//        } else {
//            LazyColumn(modifier = Modifier.weight(1f)) {
//                items(cartItems) { cartItem ->
//                    CartItemRow(cartItem, cartViewModel)
//                }
//            }
//
//            Text(
//                text = "Total: ₵${"%.2f".format(cartViewModel.totalCost.value)}",
//                style = MaterialTheme.typography.titleLarge,
//                modifier = Modifier.padding(vertical = 8.dp)
//            )
//            Button(
//                onClick = { cartViewModel.clearCart() },
//                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
//            ) {
//                Text("Clear Cart")
//            }
//        }
//    }
//}

@Composable
fun CartScreen(cartViewModel: CartViewModel) {
    // Collect cartItems state from CartViewModel
    val cartItems by cartViewModel.cartItems.collectAsState()

    // Collect totalCost state from CartViewModel
    val totalCost by cartViewModel.totalCost.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Cart", style = MaterialTheme.typography.headlineSmall)

        if (cartItems.isEmpty()) {
            Text("Cart is empty", style = MaterialTheme.typography.bodyLarge)
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(cartItems) { cartItem ->
                    CartItemRow(cartItem, cartViewModel)
                }
            }

            Text(
                text = "Total: ₵${"%.2f".format(totalCost)}", // Use collected state
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
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image
            AsyncImage(
                model = cartItem.product.imageUrl,
                contentDescription = cartItem.product.name,
                modifier = Modifier
                    .size(60.dp)
                    .padding(end = 8.dp)
            )

            // Product Name and Price
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(cartItem.product.name, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "₵${cartItem.product.price}", style = MaterialTheme.typography.bodyMedium)
            }

            // Quantity Control
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { cartViewModel.decreaseQuantity(cartItem) },
                    shape = RoundedCornerShape(30),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF800080))
                ) {
                    Text("-")
                }

                Text(
                    text = "${cartItem.quantity}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Button(
                    onClick = { cartViewModel.increaseQuantity(cartItem) },
                    shape = RoundedCornerShape(30),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF800080))
                ) {
                    Text("+")
                }
            }
        }
    }
}