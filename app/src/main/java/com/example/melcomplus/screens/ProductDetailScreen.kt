package com.example.melcomplus.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.melcomplus.components.TopNavigationBar
import com.example.melcomplus.models.Product
import com.example.melcomplus.viewmodels.CartViewModel

@Composable
fun ProductDetailScreen(
    product: Product,
    cartViewModel: CartViewModel,
    onBackClick: () -> Unit // Add onBackClick for TopNavigationBar
) {
    // Collect cartItems state from CartViewModel
    val cartItems by cartViewModel.cartItems.collectAsState()

    // Derive itemCount from cartItems
    val cartItem = cartItems.find { it.product.name == product.name }
    var itemCount by remember { mutableStateOf(cartItem?.quantity ?: 0) }

    Scaffold(
        topBar = {
            TopNavigationBar(
//                title = product.name, // Set the title for the Top App Bar
                title = "",
                onBackClick = onBackClick // Handle back navigation
            )

        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Use paddingValues from Scaffold
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Load product image using Coil
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Product Name
            Text(
                text = product.name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Price and Add to Cart row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Product Price
                Text(
                    text = "₵${product.price}",
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp)
                )

                // Add to Cart / Quantity Control Button
                if (itemCount == 0) {
                    // Initial Add to Cart button
                    Button(
                        onClick = {
                            cartViewModel.addToCart(product)
                            itemCount++
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF008080), // Teal color
                            contentColor = Color.White
                        ),
                        border = BorderStroke(1.dp, Color(0xFF008080)),
                        modifier = Modifier.width(150.dp), // Set a fixed width
                        contentPadding = PaddingValues(4.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add to Cart")
                        Text("Add to Cart")
                    }
                } else {
                    // Quantity control buttons
                    Row(
                        modifier = Modifier
                            .width(150.dp) // Set a fixed width
                            .background(Color(0xFF46389A), shape = RoundedCornerShape(8.dp))
                            .border(1.dp, Color(0xFF46389A), shape = RoundedCornerShape(8.dp)),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = {
                                cartItem?.let {
                                    if (it.quantity > 1) {
                                        cartViewModel.decreaseQuantity(it)
                                        itemCount--
                                    } else {
                                        cartViewModel.removeFromCart(it)
                                        itemCount = 0
                                    }
                                }
                            },
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(2.dp)
                        ) {
                            Text("-", color = Color.White)
                        }

                        Text(
                            text = "$itemCount",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        TextButton(
                            onClick = {
                                cartItem?.let { cartViewModel.increaseQuantity(it) }
                                itemCount++
                            },
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(2.dp)
                        ) {
                            Text("+", color = Color.White)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Product Details Section
            Text(
                text = "Product Details",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.details,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth(),
//                    .padding(8.dp),
                textAlign = TextAlign.Left
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailScreenPreview() {
    val sampleProduct = Product(
        name = "BETTY CROCKER SUPERMOIST CAKEMIX CARROT 425G",
        details = "Betty Crocker Supermoist Cakemix Carrot 425G",
        price = 71.99,
        imageUrl = "https://demo8.1hour.in/media/products/18366.png"
    )

    val navController = androidx.navigation.compose.rememberNavController()
    ProductDetailScreen(
        product = sampleProduct,
        cartViewModel = CartViewModel(),
        onBackClick = { navController.popBackStack() } // Handle back navigation
    )
}