package com.example.melcomplus.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.melcomplus.models.Product
import com.example.melcomplus.viewmodels.CartViewModel

@Composable
fun ProductDetailScreen(product: Product, cartViewModel: CartViewModel = viewModel()) {
    var itemCount by remember { mutableStateOf(0) } // Track item count for the cart

    Column(
        modifier = Modifier
            .fillMaxSize()
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
                        itemCount++
                        cartViewModel.addToCart(product)
                    },
                    shape = RoundedCornerShape(10),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008080)) // Teal color
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add to Cart")
                }
            } else {
                // Quantity control buttons
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Decrease button
                    Button(
                        onClick = {
                            if (itemCount > 0) itemCount--
                        },
                        shape = RoundedCornerShape(10),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF800080)) // Purple color
                    ) {
                        Text("-")
                    }
                    Spacer(modifier = Modifier.width(8.dp))

                    // Item count display
                    Text(text = "$itemCount", style = MaterialTheme.typography.headlineMedium)

                    Spacer(modifier = Modifier.width(8.dp))

                    // Increase button
                    Button(
                        onClick = { itemCount++ },
                        shape = RoundedCornerShape(10),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF800080)) // Purple color
                    ) {
                        Text("+")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Product Details Section
        Text(
            text = "Product Details",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = product.details,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(8.dp)
        )
    }
}
