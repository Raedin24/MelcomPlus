package com.example.melcomplus.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.melcomplus.models.CartItem
import com.example.melcomplus.models.Product
import com.example.melcomplus.viewmodels.CartViewModel
import com.example.melcomplus.viewmodels.FavoritesViewModel

@Composable
fun ProductTile(
    product: Product,
    isInCart: Boolean,
    cartViewModel: CartViewModel,
    favoritesViewModel: FavoritesViewModel,
    onProductClick: (Product) -> Unit
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val cartItem = cartItems.find { it.product.name == product.name }

    val favorites by favoritesViewModel.favorites.collectAsState()
    val isFavorite = favorites.contains(product)

    val heartScale by animateFloatAsState(targetValue = if (isFavorite) 1.2f else 1f)

    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .width(125.dp)
            .height(250.dp)
            .clickable { onProductClick(product)},
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier
                .width(150.dp)
//                .height(220.dp)
                .fillMaxHeight()
        ) {
            // Product Image + Favorite Icon
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.name,
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                )

                IconButton(
                    onClick = {
                        if (isFavorite) {
                            favoritesViewModel.removeFromFavorites(product)
                        } else {
                            favoritesViewModel.addToFavorites(product)
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .scale(heartScale)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Red else Color.Gray
                    )
                }
            }

            // Add to Cart or Quantity Controls
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(horizontal = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                if (!isInCart) {
                    Button(
                        onClick = { cartViewModel.addToCart(product) },
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFF46389A)
                        ),
                        border = BorderStroke(1.dp, Color(0xFF46389A)),
                        modifier = Modifier
                            .width(140.dp)
                            .height(30.dp),
                        contentPadding = PaddingValues(5.dp)
                    ) {
                        Text(
                            text= "Add",
                            style = MaterialTheme.typography.labelLarge.copy(
                                lineHeight = 16.sp, // tighter line spacing
                                fontSize = 14.sp
                            ),
                            maxLines = 1
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .width(140.dp)
                            .height(30.dp)
                            .background(Color(0xFF46389A), shape = RoundedCornerShape(15.dp)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = {
                                cartItem?.let {
                                    if (it.quantity > 1) cartViewModel.decreaseQuantity(it)
                                    else cartViewModel.removeFromCart(it)
                                }
                            },
                            modifier = Modifier.size(30.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("-", color = Color.White, fontSize = 18.sp)
                        }

                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${cartItem?.quantity ?: 1}",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 15.sp,
                                    lineHeight = 15.sp
                                ),
                                color = Color.White
                            )
                        }

                        TextButton(
                            onClick = { cartItem?.let { cartViewModel.increaseQuantity(it) } },
                            modifier = Modifier.size(30.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("+", color = Color.White, fontSize = 18.sp)
                        }
                    }

                }
            }

            // Price and Name
            Text(
                text = "₵${product.price}",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
//                    .height(20.dp)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            )
            Text(
                text = product.name,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                color = Color.Black,
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
//                    .height(20.dp)
                    .fillMaxHeight()
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProductTilePreview() {
    val sampleProduct = Product(
        sku = "178523",
        name = "BETTY CROCKER SUPERMOIST CAKEMIX CARROT 425G",
        details = "Delicious and easy-to-make carrot cake mix.",
        price = 71.99,
        imageUrl = "https://demo8.1hour.in/media/products/18366.png",
        type = "GROCERY"
    )

    val cartViewModelEmpty = remember { CartViewModel() }
    val favoritesViewModelEmpty = remember { FavoritesViewModel() }

    val cartViewModelFull = remember {
        CartViewModel().apply {
            addToCart(sampleProduct)
            increaseQuantity(CartItem(sampleProduct, 1))
        }
    }
    val favoritesViewModelFull = remember {
        FavoritesViewModel().apply {
            addToFavorites(sampleProduct)
        }
    }

    Column {
        Text("• Not in Cart / Not Favorite")
        ProductTile(
            product = sampleProduct,
            isInCart = false,
            cartViewModel = cartViewModelEmpty,
            favoritesViewModel = favoritesViewModelEmpty,
            onProductClick = {}
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("• In Cart / Favorite")
        ProductTile(
            product = sampleProduct,
            isInCart = true,
            cartViewModel = cartViewModelFull,
            favoritesViewModel = favoritesViewModelFull,
            onProductClick = {}
        )
    }
}
