package com.example.melcomplus.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import com.example.melcomplus.components.BottomNavigationBar
import com.example.melcomplus.data.CategoryRepository
import com.example.melcomplus.models.Category
import com.example.melcomplus.models.Product
import com.example.melcomplus.viewmodels.CartViewModel
import com.example.melcomplus.viewmodels.SearchViewModel

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SearchScreen(
//    navController: NavHostController,
//    searchViewModel: SearchViewModel = viewModel()
//) {
//    var query by remember { mutableStateOf("") }
//    val products = CategoryRepository.categories.flatMap { it.items }
//    val filteredProducts = products.filter { it.name.contains(query, ignoreCase = true) }
//    val recentSearches by remember { derivedStateOf { searchViewModel.recentSearches } }
//
//    Scaffold(
//        bottomBar = { BottomNavigationBar(navController) }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//        ) {
//            // Search bar section
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color(0xFFFFE599))
//                    .padding(15.dp, 15.dp, 15.dp, 5.dp)
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clip(RoundedCornerShape(20.dp))
//                        .background(Color.White)
//                        .padding(horizontal = 10.dp, vertical = 5.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray)
//                    Spacer(modifier = Modifier)
//                    TextField(
//                        value = query,
//                        onValueChange = { query = it },
//                        placeholder = { Text("Search Products") },
//                        singleLine = true,
//                        modifier = Modifier.weight(1f),
//                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
//                        keyboardActions = KeyboardActions(
//                            onSearch = {
//                                if (query.isNotBlank()) {
//                                    searchViewModel.addToRecentSearches(query)
//                                    query = "" // Clear input after search
//                                }
//                            }
//                        ),
//                        colors = TextFieldDefaults.textFieldColors(
//                            containerColor = Color.White, // Ensure background is white
//                            unfocusedIndicatorColor = Color.Transparent,
//                            focusedIndicatorColor = Color.Transparent,
//                            disabledIndicatorColor = Color.Transparent
//                        )
//                    )
//                    if (query.isNotEmpty()) {
//                        IconButton(onClick = { query = "" }) {
//                            Icon(Icons.Default.Close, contentDescription = "Clear")
//                        }
//                    }
//                }
//            }
//
//            // Recent searches section
//            if (recentSearches.isNotEmpty()) {
//                Column(modifier = Modifier.padding(16.dp)) {
//                    Text(
//                        text = "Recent Searches",
//                        style = MaterialTheme.typography.titleMedium,
//                        color = Color.Black,
//                        modifier = Modifier.padding(bottom = 8.dp)
//                    )
//
//                    LazyRow(
//                        horizontalArrangement = Arrangement.spacedBy(8.dp),
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        items(recentSearches) { search ->
//                            Box(
//                                modifier = Modifier
//                                    .clip(RoundedCornerShape(50))
//                                    .background(Color.White)
//                                    .border(1.dp, Color.Gray, RoundedCornerShape(50))
//                                    .clickable { query = search }
//                                    .padding(horizontal = 16.dp, vertical = 8.dp)
//                            ) {
//                                Text(
//                                    text = search,
//                                    textAlign = TextAlign.Center,
//                                    fontSize = 14.sp,
//                                    color = Color.Black
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//
//            // Display search results only when query is not empty
//            if (query.isNotEmpty()) {
//                LazyColumn {
//                    items(filteredProducts) { product ->
//                        Text(
//                            text = product.name,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(8.dp)
//                                .clickable {
//                                    searchViewModel.addToRecentSearches(query)
//                                    navController.navigate("productDetail/${product.name}")
//                                }
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewSearchScreen() {
//    val mockNavController = rememberNavController()
//    SearchScreen(navController = mockNavController)
//}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    searchViewModel: SearchViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel(),
    categories: List<Category> // Pass categories from the parent
) {
    var query by remember { mutableStateOf("") }
    val products = categories.flatMap { it.items }
    val filteredProducts = products.filter { it.name.contains(query, ignoreCase = true) }
    val recentSearches by remember { derivedStateOf { searchViewModel.recentSearches } }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()) // Allow scrolling if content overflows
        ) {
            // Search bar section (unchanged)
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
                    Spacer(modifier = Modifier)
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
                                    query = "" // Clear input after search
                                }
                            }
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.White, // Ensure background is white
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

            // Recent searches section (unchanged)
            if (recentSearches.isNotEmpty()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Recent Searches",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
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

            // Display search results only when query is not empty
            if (query.isNotEmpty()) {
                LazyColumn {
                    items(filteredProducts) { product ->
                        Text(
                            text = product.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    searchViewModel.addToRecentSearches(query)
                                    navController.navigate("productDetail/${product.name}")
                                }
                        )
                    }
                }
            } else {
                // Add OrderAgainSection with the placed orders
                OrderAgainSection(
                    cartViewModel = cartViewModel, // Pass CartViewModel
                    onProductClick = { product ->
                        navController.navigate("productDetail/${product.name}")
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // BestSellerSection (unchanged)
                BestSellerSection(
                    categories = categories, // Pass the same categories list
                    cartViewModel = cartViewModel,
                    onProductClick = { product ->
                        navController.navigate("productDetail/${product.name}")
                    }
                )
            }
        }
    }
}

//@Composable
//fun SearchScreen(
//    navController: NavHostController,
//    searchViewModel: SearchViewModel = viewModel(),
//    cartViewModel: CartViewModel = viewModel()
//) {
//    var query by remember { mutableStateOf("") }
//    val products = CategoryRepository.categories.flatMap { it.items }
//    val filteredProducts = products.filter { it.name.contains(query, ignoreCase = true) }
//    val recentSearches by remember { derivedStateOf { searchViewModel.recentSearches } }
//
//    Scaffold(
//        bottomBar = { BottomNavigationBar(navController) }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//        ) {
//            // Search bar section (unchanged)
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color(0xFFFFE599))
//                    .padding(15.dp, 15.dp, 15.dp, 5.dp)
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clip(RoundedCornerShape(20.dp))
//                        .background(Color.White)
//                        .padding(horizontal = 10.dp, vertical = 5.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray)
//                    Spacer(modifier = Modifier)
//                    TextField(
//                        value = query,
//                        onValueChange = { query = it },
//                        placeholder = { Text("Search Products") },
//                        singleLine = true,
//                        modifier = Modifier.weight(1f),
//                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
//                        keyboardActions = KeyboardActions(
//                            onSearch = {
//                                if (query.isNotBlank()) {
//                                    searchViewModel.addToRecentSearches(query)
//                                    query = "" // Clear input after search
//                                }
//                            }
//                        ),
//                        colors = TextFieldDefaults.textFieldColors(
//                            containerColor = Color.White, // Ensure background is white
//                            unfocusedIndicatorColor = Color.Transparent,
//                            focusedIndicatorColor = Color.Transparent,
//                            disabledIndicatorColor = Color.Transparent
//                        )
//                    )
//                    if (query.isNotEmpty()) {
//                        IconButton(onClick = { query = "" }) {
//                            Icon(Icons.Default.Close, contentDescription = "Clear")
//                        }
//                    }
//                }
//            }
//
//            // Recent searches section (unchanged)
//            if (recentSearches.isNotEmpty()) {
//                Column(modifier = Modifier.padding(16.dp)) {
//                    Text(
//                        text = "Recent Searches",
//                        style = MaterialTheme.typography.titleMedium,
//                        color = Color.Black,
//                        modifier = Modifier
////                            .padding(bottom = 8.dp)
//                    )
//
//                    LazyRow(
//                        horizontalArrangement = Arrangement.spacedBy(8.dp),
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        items(recentSearches) { search ->
//                            Box(
//                                modifier = Modifier
//                                    .clip(RoundedCornerShape(50))
//                                    .background(Color.White)
//                                    .border(1.dp, Color.Gray, RoundedCornerShape(50))
//                                    .clickable { query = search }
//                                    .padding(horizontal = 16.dp, vertical = 8.dp)
//                            ) {
//                                Text(
//                                    text = search,
//                                    textAlign = TextAlign.Center,
//                                    fontSize = 14.sp,
//                                    color = Color.Black
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//
//            // Display search results only when query is not empty
//            if (query.isNotEmpty()) {
//                LazyColumn {
//                    items(filteredProducts) { product ->
//                        Text(
//                            text = product.name,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(8.dp)
//                                .clickable {
//                                    searchViewModel.addToRecentSearches(query)
//                                    navController.navigate("productDetail/${product.name}")
//                                }
//                        )
//                    }
//                }
//            } else {
//                // Add OrderAgainSection with the placed orders
//                OrderAgainSection(
//                    cartViewModel = cartViewModel, // Pass CartViewModel
//                    onProductClick = { product ->
//                        navController.navigate("productDetail/${product.name}")
//                    }
//                )
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // BestSellerSection (unchanged)
//                BestSellerSection(
//                    categories = CategoryRepository.categories,
//                    cartViewModel = cartViewModel,
//                    onProductClick = { product ->
//                        navController.navigate("productDetail/${product.name}")
//                    }
//                )
//            }
//        }
//    }
//}

@Preview(showBackground = true)
@Composable
fun PreviewSearchScreen() {
    val mockNavController = rememberNavController()
    val mockSearchViewModel = SearchViewModel() // Mock SearchViewModel
    val mockCartViewModel = CartViewModel() // Mock CartViewModel

    // Mock categories data for preview
    val mockCategories = listOf(
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
        ),
        Category(
            name = "BEVERAGES",
            icon = "beverages/icon.png",
            items = listOf(
                Product(
                    name = "COCA-COLA 1.5L",
                    details = "Refreshing Coca-Cola 1.5L",
                    price = 5.99,
                    imageUrl = "https://demo8.1hour.in/media/products/18367.png"
                )
            )
        )
    )

    SearchScreen(
        navController = mockNavController,
        searchViewModel = mockSearchViewModel,
        cartViewModel = mockCartViewModel,
        categories = mockCategories // Pass mock categories
    )
}