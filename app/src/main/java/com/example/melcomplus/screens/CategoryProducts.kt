package com.example.melcomplus.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.melcomplus.Screen
import com.example.melcomplus.components.ProductTile
import com.example.melcomplus.components.TopNavigationBar
import com.example.melcomplus.data.CategoryRepository
import com.example.melcomplus.models.Category
import com.example.melcomplus.viewmodels.CartViewModel
import com.example.melcomplus.viewmodels.FavoritesViewModel
import kotlinx.coroutines.launch

@Composable
fun CategoryProductsScreen(
    navController: NavHostController,
    categoryName: String,
    cartViewModel: CartViewModel,
    favoritesViewModel: FavoritesViewModel,
    onBackClick: () -> Unit
) {
    // Find the category by name
    val category = CategoryRepository.categories.find { it.name == categoryName }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Add the TopNavigationBar at the top
        TopNavigationBar(
            title = "Category", // Use the category name as the title
            onBackClick = onBackClick
        )

        if (category != null) {
            val categories = CategoryRepository.categories // Use all categories

            // Calculate the initial page index based on the categoryName
            val initialPageIndex = categories.indexOfFirst { it.name == categoryName }
                .coerceAtLeast(0) // Ensure it's not negative

            // Initialize pagerState with the correct initial page
            val pagerState = rememberPagerState(
                pageCount = { categories.size },
                initialPage = initialPageIndex
            )

            val coroutineScope = rememberCoroutineScope()

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                CategoryNavigationBar(
                    categories = categories,
                    selectedCategoryIndex = pagerState.currentPage,
                    onCategorySelected = { index ->
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )

                HorizontalPager(
                    state = pagerState, // Pass the pagerState
                    modifier = Modifier.fillMaxSize().weight(1f)
                ) { page ->
                    val currentCategory = categories.getOrNull(page)
                    if (currentCategory != null) {
                        CategoryProductsPage(
                            category = currentCategory,
                            navController = navController,
                            cartViewModel = cartViewModel,
                            favoritesViewModel = favoritesViewModel
                        )
                    }
                }
            }
        } else {
            // Handle case where category is not found
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Category not found")
            }
        }
    }
}

@Composable
fun CategoryNavigationBar(
    categories: List<Category>,
    selectedCategoryIndex: Int,
    onCategorySelected: (Int) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            CategoryTab(
                category = category,
                isSelected = categories.indexOf(category) == selectedCategoryIndex,
                onClick = { onCategorySelected(categories.indexOf(category)) }
            )
        }
    }
}

@Composable
fun CategoryTab(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(70)) // Capsule shape
            .background(if (isSelected) Color(0xFFFAE8A6) else Color.Transparent) // Light green when selected
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = category.name,
            style = MaterialTheme.typography.bodyLarge,
            color = if (isSelected) Color(0xFF20D78B) else Color.Gray,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp) // Extra padding for better look
        )
    }
}

@Composable
fun CategoryProductsPage(
    category: Category,
    navController: NavHostController,
    cartViewModel: CartViewModel,
    favoritesViewModel: FavoritesViewModel
) {
    println("Category items: ${category.items}") // Log the items
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize().padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(category.items) { product -> // Ensure it's a List<Product>
            println("Product: $product") // Log each product
            ProductTile(
                product = product,
                cartViewModel = cartViewModel,
                favoritesViewModel = favoritesViewModel,
                onProductClick = {
                    navController.navigate(Screen.ProductDetail.createRoute(product.name))
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCategoryProductsScreen() {
    val navController = rememberNavController()
    val cartViewModel = CartViewModel()
    val favoritesViewModel = FavoritesViewModel()

    CategoryProductsScreen(
        navController = navController,
        categoryName = CategoryRepository.categories.firstOrNull()?.name ?: "Default",
        cartViewModel = cartViewModel,
        favoritesViewModel = favoritesViewModel,
        onBackClick = {}
    )
}
