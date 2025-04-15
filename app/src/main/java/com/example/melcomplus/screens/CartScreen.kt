// CartScreen.kt
package com.example.melcomplus.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.melcomplus.components.TopNavigationBar
import com.example.melcomplus.models.CartItem
import com.example.melcomplus.models.Product
import com.example.melcomplus.viewmodels.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    navController: NavHostController,
    onBackClick: () -> Unit
) {
    // Collect cartItems state from CartViewModel
    val cartItems by cartViewModel.cartItems.collectAsState()
    val totalCost by cartViewModel.totalCost.collectAsState()

    Scaffold(
        topBar = {
            TopNavigationBar(
                title = "Cart",
                onBackClick = onBackClick
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (cartItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Cart is empty", style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                // Delivery Section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.DirectionsBike,
                        contentDescription = "Delivery",
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Delivery",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                        Text(
                            text = "Delivery in 16 mins",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }

                // Cart Items List
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(cartItems) { cartItem ->
                        CartItemRow(cartItem, cartViewModel, navController)
                    }
                }

                // Total Cost & Clear Cart Button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total: ₵${"%.2f".format(totalCost)}",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Button(
                        onClick = { cartViewModel.clearCart() },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
                    ) {
                        Text("Clear Cart")
                    }
                }

                // Place Order Button
                Button(
                    onClick = { cartViewModel.placeOrder(cartItems.map { it.product }) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF46389A))
                ) {
                    Text("Place Order", color = Color.White)
                }
            }
        }
    }
}


@Composable
fun CartItemRow(cartItem: CartItem, cartViewModel: CartViewModel, navController: NavHostController) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                navController.navigate("productDetail/${cartItem.product.name}")
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image
            AsyncImage(
                model = cartItem.product.imageUrl,
                contentDescription = cartItem.product.name,
                modifier = Modifier
                    .size(60.dp)
                    .height(100.dp)
            )

            // Product Name and Price
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(cartItem.product.name, style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 18.sp))
                Text(text = "₵${cartItem.product.price}", style = MaterialTheme.typography.bodyMedium)
            }

            // Quantity Control (Styled Like ProductTile)
            Row(
                modifier = Modifier
                    .background(Color(0xFF46389A), shape = RoundedCornerShape(20.dp))
                    .height(30.dp)
                    ,horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = {
                        if (cartItem.quantity > 1) {
                            cartViewModel.decreaseQuantity(cartItem)
                        } else {
                            cartViewModel.removeFromCart(cartItem)
                        }
                    },
                    contentPadding = PaddingValues(2.dp)
                ) {
                    Text("-", color = Color.White)
                }

                Text(
                    text = "${cartItem.quantity}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                TextButton(
                    onClick = { cartViewModel.increaseQuantity(cartItem) },
                    contentPadding = PaddingValues(2.dp)
                ) {
                    Text("+", color = Color.White)
                }
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CartScreenPreview() {
    val mockCartViewModel = CartViewModel()
    val sampleProduct = Product(
        sku = "178523",
        name = "BETTY CROCKER SUPERMOIST CAKEMIX CARROT 425G",
        details = "Delicious carrot cake mix.",
        price = 71.99,
        imageUrl = "https://demo8.1hour.in/media/products/18366.png",
        type = "GROCERY"
    )

    // Add mock data
    mockCartViewModel.addToCart(sampleProduct)
    mockCartViewModel.addToCart(sampleProduct)

    CartScreen(
        cartViewModel = mockCartViewModel,
        navController = rememberNavController(),
        onBackClick = {}
    )
}
