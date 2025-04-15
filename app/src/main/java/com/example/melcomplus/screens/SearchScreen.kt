@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.melcomplus.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.melcomplus.models.Category
import com.example.melcomplus.models.Product
import com.example.melcomplus.models.Subcategory
import com.example.melcomplus.viewmodels.CartViewModel
import com.example.melcomplus.viewmodels.FavoritesViewModel
import com.example.melcomplus.viewmodels.SearchViewModel

@Composable
fun SearchScreen(
    navController: NavHostController,
    selectedType: String, // ✅ Type filter added
    searchViewModel: SearchViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel(),
    favoritesViewModel: FavoritesViewModel = viewModel(),
    categories: List<Category>
) {
    var query by remember { mutableStateOf("") }

    // All products
    val allProducts = categories.flatMap { it.subcategories.flatMap { sub -> sub.products } }

    // Search filter
    val filteredProducts = allProducts.filter { it.name.contains(query, ignoreCase = true) }

    // Recent searches
    val recentSearches by remember { derivedStateOf { searchViewModel.recentSearches } }

    // ✅ Filtered categories by selectedType for best sellers
    val filteredCategories = remember(selectedType, categories) {
        categories.filter { cat ->
            cat.subcategories.any { sub ->
                sub.products.any { it.type.equals(selectedType, ignoreCase = true) }
            }
        }.map { cat ->
            cat.copy(
                subcategories = cat.subcategories.map { sub ->
                    sub.copy(products = sub.products.filter {
                        it.type.equals(selectedType, ignoreCase = true)
                    })
                }.filter { it.products.isNotEmpty() }
            )
        }.filter { it.subcategories.isNotEmpty() }
    }

    Scaffold(containerColor = Color.White) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFFE599))
                        .padding(15.dp, 15.dp, 15.dp, 5.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White)
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray)
                        Spacer(modifier = Modifier.width(8.dp))
                        TextField(
                            value = query,
                            onValueChange = { query = it },
                            placeholder = { Text("Search Products") },
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    if (query.isNotBlank()) {
                                        searchViewModel.addToRecentSearches(query)
                                    }
                                }
                            ),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            )
                        )
                        if (query.isNotEmpty()) {
                            IconButton(onClick = { query = "" }) {
                                Icon(Icons.Default.Close, contentDescription = "Clear")
                            }
                        }
                    }
                }
            }

            if (recentSearches.isNotEmpty()) {
                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Recent Searches",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(recentSearches) { search ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(50))
                                        .background(Color.White)
                                        .border(1.dp, Color.Gray, RoundedCornerShape(50))
                                        .clickable { query = search }
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        text = search,
                                        textAlign = TextAlign.Center,
                                        fontSize = 14.sp,
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                    }
                }
            }

            if (query.isNotEmpty()) {
                items(filteredProducts) { product ->
                    Text(
                        text = product.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                searchViewModel.addToRecentSearches(product.name)
                                navController.navigate("productDetail/${product.name}")
                            }
                    )
                }
            } else {
                item {
                    OrderAgainSection(
                        cartViewModel = cartViewModel,
                        favoritesViewModel = favoritesViewModel,
                        onProductClick = {
                            navController.navigate("productDetail/${it.name}")
                        }
                    )
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                item {
                    BestSellerSection(
                        categories = filteredCategories, // ✅ Filtered by type
                        cartViewModel = cartViewModel,
                        favoritesViewModel = favoritesViewModel,
                        onProductClick = {
                            navController.navigate("productDetail/${it.name}")
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchScreen() {
    val mockNavController = rememberNavController()
    val mockSearchViewModel = SearchViewModel().apply { addToRecentSearches("cake") }
    val mockCartViewModel = CartViewModel()
    val mockFavoritesViewModel = FavoritesViewModel()

    val mockCategories = listOf(
        Category(
            name = "FOOD CUPBOARD",
            icon = "pizzimg/pizza.png",
            subcategories = listOf(
                Subcategory(
                    name = "CAKE MIXES",
                    imageUrl = "https://demo8.1hour.in/media/productsSubCategory/bbq-meat-feast3867.png",
                    products = listOf(
                        Product(
                            sku = "178523",
                            name = "BETTY CROCKER SUPERMOIST CAKEMIX CARROT 425G",
                            details = "Betty Crocker Supermoist Cakemix Carrot 425G",
                            price = 71.99,
                            imageUrl = "https://demo8.1hour.in/media/products/18366.png",
                            type = "GROCERY"
                        ),
                        Product(
                            sku = "183556",
                            name = "GOYA VIENNA SAUSAGE 142Gms 46Oz",
                            details = "Goya Vienna Sausage 142Gms 46Oz",
                            price = 2.40,
                            imageUrl = "https://demo8.1hour.in/media/products/18356.png",
                            type = "GROCERY"
                        )
                    )
                )
            )
        )
    )

    SearchScreen(
        navController = mockNavController,
        selectedType = "GROCERY", // ✅ Preview using GROCERY type
        searchViewModel = mockSearchViewModel,
        cartViewModel = mockCartViewModel,
        favoritesViewModel = mockFavoritesViewModel,
        categories = mockCategories
    )
}
