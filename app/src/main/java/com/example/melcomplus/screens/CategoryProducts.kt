package com.example.melcomplus.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.melcomplus.Screen
import com.example.melcomplus.data.CategoryRepository
import com.example.melcomplus.models.Category
import com.example.melcomplus.models.Product
import com.example.melcomplus.viewmodels.CartViewModel
import com.example.melcomplus.components.ProductTile
import kotlinx.coroutines.launch

@Composable
fun CategoryProductsScreen(
    navController: NavHostController,
    categoryName: String,
    cartViewModel: CartViewModel
) {
    // Find the category by name
    val category = CategoryRepository.categories.find { it.name == categoryName }

    if (category != null) {
        val categories = CategoryRepository.categories // Use all categories

        // Corrected pagerState initialization
        val pagerState = rememberPagerState(pageCount = { categories.size }, initialPage = 0)

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
                        cartViewModel = cartViewModel
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
    Text(
        text = category.name,
        style = MaterialTheme.typography.bodyLarge,
        color = if (isSelected) Color(0xFF800080) else Color.Gray,
        modifier = Modifier.clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}

@Composable
fun CategoryProductsPage(
    category: Category,
    navController: NavHostController,
    cartViewModel: CartViewModel
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
                onProductClick = {
                    navController.navigate(Screen.ProductDetail.createRoute(product.name))
                }
            )
        }
    }
}