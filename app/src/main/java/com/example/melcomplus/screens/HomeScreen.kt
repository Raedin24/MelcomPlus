package com.example.melcomplus.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.melcomplus.R
import com.example.melcomplus.components.ProductTile
import com.example.melcomplus.models.Category
import com.example.melcomplus.models.Product
import com.example.melcomplus.viewmodels.CartViewModel
import com.example.melcomplus.viewmodels.FavoritesViewModel


@Composable
fun HomeScreen(
    categories: List<Category>,
    onCategoryClick: (String) -> Unit,
    cartViewModel: CartViewModel,
    favoritesViewModel: FavoritesViewModel,
    onProductClick: (Product) -> Unit,
) {
    Scaffold (
        containerColor = Color.White
    ){ paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(bottom = 16.dp) // Prevents overlap with bottom bar
        ) {
            item {
                Text(
                    text = "Categories",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black,
                    modifier = Modifier.padding(10.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Categories in a static 3-column grid
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val rows = categories.chunked(3) // Split categories into rows of 3
                    rows.forEach { rowCategories ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            rowCategories.forEach { category ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f) // Makes each card take equal space
                                        .aspectRatio(1f) // Ensures a square layout
                                ) {
                                    CategoryCard(
                                        category = category,
                                        onClick = { onCategoryClick(category.name) }
                                    )
                                }
                            }
                            // Fill empty spaces if the last row has less than 3 items
                            repeat(3 - rowCategories.size) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
//                OrderAgainSection(categories, cartViewModel, onProductClick)
                OrderAgainSection(
                    cartViewModel = cartViewModel,
                    favoritesViewModel = favoritesViewModel,
                    onProductClick = onProductClick
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                BestSellerSection(categories, cartViewModel, favoritesViewModel, onProductClick)
            }
        }
    }
}



@Composable
fun CategoryCard(category: Category, onClick: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(4.dp)
//            .clickable(onClick = onClick)
            .clickable { onClick.invoke(category.name) }
            .clip(RoundedCornerShape(12.dp))
    ) {
        // Background Image
        AsyncImage(
            model = category.icon,
            contentDescription = category.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        // Text Overlay on Top Left
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.4f)) // Semi-transparent overlay
                .padding(8.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Text(
                text = category.name,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                fontSize = 14.sp,
            )
        }
    }
}


@Composable
fun OrderAgainSection(
    cartViewModel: CartViewModel,
    favoritesViewModel: FavoritesViewModel,
    onProductClick: (Product) -> Unit
) {
    // Collect flow as state
    val orderAgainProducts = cartViewModel.placedOrders.collectAsState(initial = emptyList()).value

    if (orderAgainProducts.isNotEmpty()) {
        Column(
            modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp)
        ) {
            Text(
                text = "Order Again",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(orderAgainProducts.take(5)) { product -> // Take only first 5 products
                    ProductTile(
                        product = product,
                        cartViewModel = cartViewModel,
                        favoritesViewModel = favoritesViewModel,
                        onProductClick = { onProductClick(product) }
                    )
                }
            }
        }
    }
}



@Composable
fun BestSellerSection(
    categories: List<Category>,
    cartViewModel: CartViewModel,
    favoritesViewModel: FavoritesViewModel,
    onProductClick: (Product) -> Unit
) {
    val bestSellerProducts = categories.flatMap { it.items.take(2) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
    ) {
        // Use painterResource instead of AsyncImage for local drawable
        Image(
            painter = painterResource(id = R.drawable.splashscreen),
            contentDescription = "Best Seller Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()

        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
//                .background(Color.Black.copy(alpha = 0.3f)) // Optional dark overlay
        ) {
            Text(
                text = "Best Seller",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .background(Color.Black.copy(alpha = 0.3f)) // Optional dark overlay
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(3.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(bestSellerProducts) { product ->
                    ProductTile(
                        product = product,
                        cartViewModel = cartViewModel,
                        favoritesViewModel = favoritesViewModel,
                        onProductClick = onProductClick
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val sampleCategories = listOf(
        Category(
            name = "FOOD CUPBOARD",
            icon = "pizzimg/pizza.png",
            items = listOf(
                Product(
                    name = "BETTY CROCKER SUPERMOIST CAKEMIX CARROT 425G",
                    details = "Betty Crocker Supermoist Cakemix Carrot 425G",
                    price = 71.99,
                    imageUrl = "https://demo8.1hour.in/media/products/18366.png"
                ),
                Product(
                    name = "GOYA VIENNA SAUSAGE 142Gms 46Oz",
                    details = "Goya Vienna Sausage 142Gms 46Oz",
                    price = 2.40,
                    imageUrl = "https://demo8.1hour.in/media/products/18356.png"
                )
            )
        )
    )

//    val navController = androidx.navigation.compose.rememberNavController()
    val navController = rememberNavController()
    // Fix: Define onCategoryClick properly
    val onCategoryClick: (String) -> Unit = { categoryName ->
        navController.navigate("category/$categoryName")
    }

    HomeScreen(
        categories = sampleCategories,
        onCategoryClick = onCategoryClick,
        cartViewModel = CartViewModel(),
        favoritesViewModel = FavoritesViewModel(),
        onProductClick = {},
    )
}