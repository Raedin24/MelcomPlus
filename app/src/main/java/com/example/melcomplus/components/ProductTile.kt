package com.example.melcomplus.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.melcomplus.models.CartItem
import com.example.melcomplus.models.Product
import com.example.melcomplus.viewmodels.CartViewModel

//@Composable
//fun ProductTile(
//    product: Product,
//    cartViewModel: CartViewModel,
//    onProductClick: (Product) -> Unit
//) {
//    val cartItems by cartViewModel.cartItems.collectAsState()
//    val cartItem = cartItems.find { it.product.name == product.name }
//
//    var itemCount by remember { mutableStateOf(cartItem?.quantity ?: 0) }
//
//    Card(
//        shape = RoundedCornerShape(15.dp),
//        modifier = Modifier
//            .fillMaxWidth(0.5f) // 50% of parent width
//            .height(250.dp)
//            .clickable { onProductClick(product) },
//        colors = CardDefaults.cardColors(containerColor = Color.White)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize(),
//            verticalArrangement = Arrangement.SpaceBetween
//        ) {
//            // Product Image
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(1f)
//                    .padding(8.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                AsyncImage(
//                    model = product.imageUrl,
//                    contentDescription = product.name,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(100.dp)
//                )
//            }
//
//            // Add to Cart / Quantity Control (Moved Here)
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(42.dp)
//                    .padding(horizontal = 8.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                if (itemCount == 0) {
//                    Button(
//                        onClick = {
//                            cartViewModel.addToCart(product)
//                            itemCount++
//                        },
//                        shape = RoundedCornerShape(15.dp),
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = Color.White,
//                            contentColor = Color(0xFF46389A)
//                        ),
//                        border = BorderStroke(1.dp, Color(0xFF46389A)),
//                        modifier = Modifier.fillMaxSize(),
//                        contentPadding = PaddingValues(8.dp)
//                    ) {
//                        Text("Add to Cart")
//                    }
//                } else {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .background(Color(0xFF46389A), shape = RoundedCornerShape(15.dp))
//                            .padding(horizontal = 3.dp),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        TextButton(
//                            onClick = {
//                                cartItem?.let {
//                                    if (it.quantity > 1) {
//                                        cartViewModel.decreaseQuantity(it)
//                                        itemCount--
//                                    } else {
//                                        cartViewModel.removeFromCart(it)
//                                        itemCount = 0
//                                    }
//                                }
//                            },
//                            modifier = Modifier.weight(1f),
//                            contentPadding = PaddingValues(2.dp)
//                        ) {
//                            Text("-", color = Color.White)
//                        }
//
//                        Text(
//                            text = "$itemCount",
//                            style = MaterialTheme.typography.bodyMedium,
//                            color = Color.White,
//                            modifier = Modifier.padding(horizontal = 4.dp)
//                        )
//
//                        TextButton(
//                            onClick = {
//                                cartItem?.let { cartViewModel.increaseQuantity(it) }
//                                itemCount++
//                            },
//                            modifier = Modifier.weight(1f),
//                            contentPadding = PaddingValues(2.dp)
//                        ) {
//                            Text("+", color = Color.White)
//                        }
//                    }
//                }
//            }
//
//            // Product Price (Bold, Moved Up)
//            Text(
//                text = "₵${product.price}",
//                style = MaterialTheme.typography.titleMedium, // Bold style
//                color = Color.Black,
//                modifier = Modifier
//                    .padding(top = 8.dp, bottom = 4.dp)
//                    .align(Alignment.Start)
//                    .padding(horizontal = 8.dp)
//            )
//
//            // Product Name (Gray and Smaller, Placed Last)
//            Text(
//                text = product.name,
//                style = MaterialTheme.typography.bodySmall,
//                color = Color.Black,
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis,
//                modifier = Modifier
//                    .padding(bottom = 8.dp)
//                    .align(Alignment.Start)
//                    .padding(horizontal = 8.dp)
//            )
//        }
//    }
//}
//
//
//


@Composable
fun ProductTile(
    product: Product,
    cartViewModel: CartViewModel,
    onProductClick: (Product) -> Unit
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val cartItem = cartItems.find { it.product.name == product.name }

    var itemCount by remember { mutableStateOf(cartItem?.quantity ?: 0) }

    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .width(180.dp) // Fixed width
            .height(220.dp) // Fixed height
            .clickable { onProductClick(product) },
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Product Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp), // Fixed image height
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.name,
                    modifier = Modifier
                        .width(100.dp) // Fixed image width
                        .height(100.dp) // Fixed image height
                )
            }

            // Add to Cart / Quantity Control
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .padding(horizontal = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                if (itemCount == 0) {
                    Button(
                        onClick = {
                            cartViewModel.addToCart(product)
                            itemCount++
                        },
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFF46389A)
                        ),
                        border = BorderStroke(1.dp, Color(0xFF46389A)),
                        modifier = Modifier
                            .width(140.dp) // Fixed width
                            .height(40.dp), // Fixed height
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        Text("Add to Cart", maxLines = 1)
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .width(140.dp) // Fixed width
                            .height(40.dp) // Fixed height
                            .background(Color(0xFF46389A), shape = RoundedCornerShape(15.dp))
                            .padding(horizontal = 3.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically // Ensures children are centered
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
                            modifier = Modifier
                                .width(40.dp)
                                .height(36.dp), // Matches row height
                            contentPadding = PaddingValues(0.dp) // Remove extra padding
                        ) {
                            Text("-", color = Color.White)
                        }

                        // Centering the quantity text
                        Box(
                            modifier = Modifier
                                .weight(1f) // Ensures even spacing
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$itemCount",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White
                            )
                        }

                        TextButton(
                            onClick = {
                                cartItem?.let { cartViewModel.increaseQuantity(it) }
                                itemCount++
                            },
                            modifier = Modifier
                                .width(40.dp)
                                .height(36.dp), // Matches row height
                            contentPadding = PaddingValues(0.dp) // Remove extra padding
                        ) {
                            Text("+", color = Color.White)
                        }
                    }
                }
            }
           // Product Price
            Text(
                text = "₵${product.price}",
                style = MaterialTheme.typography.titleMedium, // Bold style
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp) // Fixed height
                    .padding(horizontal = 8.dp)
            )

            // Product Name
            Text(
                text = product.name,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp) // Fixed height
                    .padding(horizontal = 8.dp)
            )
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductTilePreview() {
    val sampleProduct = Product(
        name = "BETTY CROCKER SUPERMOIST CAKEMIX CARROT 425G",
        details = "Delicious and easy-to-make carrot cake mix.",
        price = 71.99,
        imageUrl = "https://demo8.1hour.in/media/products/18366.png"
    )

    // First ViewModel: Empty Cart (Shows "Add to Cart")
    val cartViewModel1 = remember { CartViewModel() }

    // Second ViewModel: Preloaded with 2 Items
    val cartViewModel2 = remember {
        CartViewModel().apply {
            addToCart(sampleProduct)
            increaseQuantity(CartItem(sampleProduct, 1)) // Increase to 2
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        ProductTile(
            product = sampleProduct,
            cartViewModel = cartViewModel1, // "Add to Cart" version
            onProductClick = {}
        )

        ProductTile(
            product = sampleProduct,
            cartViewModel = cartViewModel2, // Preloaded cart version
            onProductClick = {}
        )
    }
}