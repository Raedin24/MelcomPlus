package com.example.melcomplus.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.melcomplus.R
import com.example.melcomplus.components.ProductTile
import com.example.melcomplus.data.CategoryRepository
import com.example.melcomplus.models.Product
import com.example.melcomplus.viewmodels.CartViewModel
import com.example.melcomplus.viewmodels.FavoritesViewModel


@Composable
fun ProductDetailScreen(
    product: Product,
    cartViewModel: CartViewModel,
    favoritesViewModel: FavoritesViewModel = FavoritesViewModel(),
    onBackClick: () -> Unit
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val cartItem = cartItems.find { it.product.name == product.name }
    val isInCart = cartItem != null
    val quantity = cartItem?.quantity ?: 0

    Scaffold(containerColor = Color.White) { _ ->
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Product Image with back arrow on top
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.name,
                    modifier = Modifier
                        .fillMaxSize()
                )

                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.TopStart)
                        .size(36.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_left),
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Product Name with gray background
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF2F2F2))
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Price and Add Button in Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "₵${product.price}",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

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
                            .height(32.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
                    ) {
                        Text("Add", maxLines = 1)
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .background(Color(0xFF46389A), shape = RoundedCornerShape(15.dp))
                            .height(32.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = {
                                if (quantity > 1) cartViewModel.decreaseQuantity(cartItem!!)
                                else cartViewModel.removeFromCart(cartItem!!)
                            },
                            modifier = Modifier.size(30.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("-", color = Color.White, fontSize = 18.sp)
                        }

                        Box(
                            modifier = Modifier
                                .width(30.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$quantity",
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                                color = Color.White
                            )
                        }

                        TextButton(
                            onClick = { cartViewModel.increaseQuantity(cartItem!!) },
                            modifier = Modifier.size(30.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("+", color = Color.White, fontSize = 18.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Product Details
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "Product Details",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = product.details,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp,
                        color = Color.Gray
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Similar Items
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "Similar Items",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                val similarItems = remember(product) {
                    CategoryRepository.categories
                        .flatMap { it.subcategories }
                        .find { sub -> sub.products.any { it.name == product.name } }
                        ?.products
                        ?.filter { it.name != product.name }
                        ?.take(10)
                        ?: emptyList()
                }

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(similarItems) { similar ->
                        ProductTile(
                            product = similar,
                            isInCart = cartItems.any { it.product.name == similar.name },
                            cartViewModel = cartViewModel,
                            favoritesViewModel = favoritesViewModel,
                            onProductClick = {}
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailScreenPreview() {
    val sampleProduct = Product(
        sku = "123",
        name = "BETTY CROCKER SUPERMOIST CAKEMIX CARROT 425G",
        details = "Betty Crocker Supermoist Cakemix Carrot 425G",
        price = 71.99,
        imageUrl = "https://demo8.1hour.in/media/products/18366.png",
        type = "GROCERY"
    )

    ProductDetailScreen(
        product = sampleProduct,
        cartViewModel = CartViewModel(),
        favoritesViewModel = FavoritesViewModel(),
        onBackClick = {}
    )
}
