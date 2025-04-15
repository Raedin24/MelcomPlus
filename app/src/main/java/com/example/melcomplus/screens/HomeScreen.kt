@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.melcomplus.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.melcomplus.R
import com.example.melcomplus.components.ProductTile
import com.example.melcomplus.data.CategoryRepository
import com.example.melcomplus.models.Category
import com.example.melcomplus.models.Product
import com.example.melcomplus.models.Subcategory
import com.example.melcomplus.viewmodels.CartViewModel
import com.example.melcomplus.viewmodels.FavoritesViewModel

@Composable
fun HomeScreen(
    categories: List<Category>,
    cartViewModel: CartViewModel,
    favoritesViewModel: FavoritesViewModel,
    onCategoryClick: (String) -> Unit,
    onProductClick: (Product) -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color(0xFFE69181)),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, top = 16.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_left),
                        contentDescription = "Back",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { onBackClick() }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Hi, Kofi!",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif
                        ),
                        color = Color.Black
                    )
                }
            }
        },
        containerColor = Color.White
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 10.dp, vertical = 16.dp)
        ) {
            item {
                Text(
                    text = "Categories",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black,
                    modifier = Modifier.padding(10.dp)
                )
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    val rows = categories.chunked(3)
                    for (rowCategories in rows) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            for (category in rowCategories) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                ) {
                                    CategoryCard(category = category, onClick = { onCategoryClick(category.name) })
                                }
                            }
                            repeat(3 - rowCategories.size) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                OrderAgainSection(cartViewModel, favoritesViewModel, onProductClick)
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
    Column(
        modifier = Modifier
            .width(140.dp)
            .wrapContentHeight()
            .padding(4.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFffe8a4))
            .clickable { onClick(category.name) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp, start = 10.dp, end = 10.dp)
        ) {
            Text(
                text = category.name,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                lineHeight = 20.sp
            )
        }

        AsyncImage(
            model = category.icon,
            contentDescription = category.name,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .heightIn(min = 100.dp, max = 120.dp)
        )
    }
}

@Composable
fun OrderAgainSection(
    cartViewModel: CartViewModel,
    favoritesViewModel: FavoritesViewModel,
    onProductClick: (Product) -> Unit
) {
    val orderAgainProducts = cartViewModel.placedOrders.collectAsState(initial = emptyList()).value
    val cartItems = cartViewModel.cartItems.collectAsState(initial = emptyList()).value

    if (orderAgainProducts.isNotEmpty()) {
        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
            Text(
                text = "Order Again",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(orderAgainProducts.take(5)) { product ->
                    val isInCart = cartItems.any { it.product.name == product.name }
                    ProductTile(
                        product = product,
                        isInCart = isInCart,
                        cartViewModel = cartViewModel,
                        favoritesViewModel = favoritesViewModel,
                        onProductClick = onProductClick
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
    val bestSellerProducts = categories
        .flatMap { it.subcategories }
        .flatMap { it.products }
        .take(10)

    val cartItems = cartViewModel.cartItems.collectAsState(initial = emptyList()).value

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.splashscreen),
            contentDescription = "Best Seller Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color(0xFFFFA726).copy(alpha = 0.3f))
        )

        Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            Text(
                text = "Best Seller",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black,
                modifier = Modifier.padding(start = 10.dp, bottom = 8.dp)
            )

            LazyRow(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                items(bestSellerProducts) { product ->
                    val isInCart = cartItems.any { it.product.name == product.name }
                    ProductTile(
                        product = product,
                        isInCart = isInCart,
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
            subcategories = listOf(
                Subcategory(
                    name = "Baking Mixes",
                    imageUrl = "https://demo8.1hour.in/media/productsSubCategory/bakingmix.png",
                    products = listOf(
                        Product(
                            sku = "123",
                            name = "CAKE MIX",
                            details = "Yummy cake mix",
                            price = 20.0,
                            imageUrl = "https://demo8.1hour.in/media/products/18366.png",
                            type = "GROCERY"
                        )
                    )
                )
            )
        )
    )

    HomeScreen(
        categories = sampleCategories,
        cartViewModel = CartViewModel(),
        favoritesViewModel = FavoritesViewModel(),
        onCategoryClick = {},
        onProductClick = {},
        onBackClick = {}
    )
}
