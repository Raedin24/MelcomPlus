// CartScreen.kt
package com.example.melcomplus.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.melcomplus.components.BottomNavigationBar
import com.example.melcomplus.components.TopNavigationBar
import com.example.melcomplus.models.CartItem
import com.example.melcomplus.models.Product
import com.example.melcomplus.viewmodels.CartViewModel

//@Composable
//fun CartScreen(
//    cartViewModel: CartViewModel,
//    navController: NavHostController, // Add navController
//    onBackClick: () -> Unit // Add onBackClick for TopNavigationBar
//) {
//    // Collect cartItems state from CartViewModel
//    val cartItems by cartViewModel.cartItems.collectAsState()
//
//    // Collect totalCost state from CartViewModel
//    val totalCost by cartViewModel.totalCost.collectAsState()
//
//    Scaffold(
//        topBar = {
//            TopNavigationBar(
//                title = "Cart", // Set the title for the Top App Bar
//                onBackClick = onBackClick // Handle back navigation
//            )
//        },
//        bottomBar = {
//            BottomNavigationBar(navController) // Add the Bottom Navigation Bar
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues), // Use paddingValues from Scaffold
////                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            if (cartItems.isEmpty()) {
//                Text("Cart is empty", style = MaterialTheme.typography.bodyLarge)
//            } else {
//                LazyColumn(modifier = Modifier.weight(1f)) {
//                    items(cartItems) { cartItem ->
//                        CartItemRow(cartItem, cartViewModel)
//                    }
//                }
//
//                Text(
//                    text = "Total: ₵${"%.2f".format(totalCost)}", // Use collected state
//                    style = MaterialTheme.typography.titleLarge,
//                    modifier = Modifier.padding(vertical = 8.dp)
//                )
//                Button(
//                    onClick = { cartViewModel.clearCart() },
//                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
//                ) {
//                    Text("Clear Cart")
//                }
//            }
//        }
//    }
//}

@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    navController: NavHostController,
    onBackClick: () -> Unit
) {
    // Collect cartItems state from CartViewModel
    val cartItems by cartViewModel.cartItems.collectAsState()

    // Collect totalCost state from CartViewModel
    val totalCost by cartViewModel.totalCost.collectAsState()

    Scaffold(
        topBar = {
            TopNavigationBar(
                title = "Cart", // Set the title for the Top App Bar
                onBackClick = onBackClick // Handle back navigation
            )
        },
        bottomBar = {
            BottomNavigationBar(navController) // Add the Bottom Navigation Bar
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), // Use paddingValues from Scaffold
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (cartItems.isEmpty()) {
                Text("Cart is empty", style = MaterialTheme.typography.bodyLarge)
            } else {
                // Delivery Section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
//                    Icon(
//                        imageVector = Icons.Default.DirectionsCar, // Use a delivery icon
//                        contentDescription = "Delivery",
//                        tint = Color.Gray
//                    )
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

                // Cart Items
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(cartItems) { cartItem ->
                        CartItemRow(cartItem, cartViewModel)
                    }
                }

                // Total Cost and Clear Cart Button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total: ₵${"%.2f".format(totalCost)}", // Use collected state
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
                    onClick = {
                        // Handle place order logic
                        cartViewModel.placeOrder(cartItems.map { it.product })
                    },
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
fun CartItemRow(cartItem: CartItem, cartViewModel: CartViewModel) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
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
//                    .fillMaxSize()
            )

            // Product Name and Price
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(cartItem.product.name, style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 18.sp))
//                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "₵${cartItem.product.price}", style = MaterialTheme.typography.bodyMedium)
            }

            // Quantity Control (Styled Like ProductTile)
            Row(
                modifier = Modifier
                    .background(Color(0xFF46389A), shape = RoundedCornerShape(20.dp))
//                    .border(1.dp, Color(0xFF46389A), shape = RoundedCornerShape(10.dp))
//                    .padding(horizontal = 3.dp), // Add horizontal padding
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
//                    modifier = Modifier.weight(1f),
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
//                    modifier = Modifier.weight(1f),
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
    val mockCartViewModel = remember { CartViewModel() } // Mock ViewModel
    val navController = rememberNavController() // Fake NavController for preview

    // Add some mock cart items
    val sampleProduct = Product(
        name = "BETTY CROCKER SUPERMOIST CAKEMIX CARROT 425G",
        details = "Delicious carrot cake mix.",
        price = 71.99,
        imageUrl = "https://via.placeholder.com/150" // Use a placeholder image
    )

    // Populate mock data
    mockCartViewModel.addToCart(sampleProduct)
    mockCartViewModel.addToCart(sampleProduct) // Add twice to test quantity changes

    CartScreen(
        cartViewModel = mockCartViewModel,
        navController = navController,
        onBackClick = {}
    )
}
