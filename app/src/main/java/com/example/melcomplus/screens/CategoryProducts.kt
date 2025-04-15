package com.example.melcomplus.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.pager.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import com.example.melcomplus.models.Product
import com.example.melcomplus.models.Subcategory
import com.example.melcomplus.viewmodels.CartViewModel
import com.example.melcomplus.viewmodels.FavoritesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryProductsScreen(
    navController: NavHostController,
    categoryName: String,
    cartViewModel: CartViewModel,
    favoritesViewModel: FavoritesViewModel,
    onBackClick: () -> Unit
) {
    val category = CategoryRepository.categories.find { it.name == categoryName }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        TopNavigationBar(title = categoryName, onBackClick = onBackClick)

        if (category != null) {
            val coroutineScope = rememberCoroutineScope()
            val categories = CategoryRepository.categories
            val pagerState = rememberPagerState(
                pageCount = { categories.size },
                initialPage = categories.indexOfFirst { it.name == categoryName }.coerceAtLeast(0)
            )

            Column(modifier = Modifier.fillMaxSize()) {
                CategoryNavigationBar(
                    categories = categories,
                    selectedCategoryIndex = pagerState.currentPage,
                    onCategorySelected = { index ->
                        coroutineScope.launch { pagerState.animateScrollToPage(index) }
                    }
                )

                HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
                    val currentCategory = categories.getOrNull(page)
                    if (currentCategory != null) {
                        SubcategoryProductSection(
                            subcategories = currentCategory.subcategories,
                            navController = navController,
                            cartViewModel = cartViewModel,
                            favoritesViewModel = favoritesViewModel
                        )
                    }
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
    val lazyListState = rememberLazyListState()
    LaunchedEffect(selectedCategoryIndex) {
        lazyListState.animateScrollToItem(selectedCategoryIndex)
    }
    LazyRow(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        state = lazyListState
    ) {
        items(categories) { category ->
            val index = categories.indexOf(category)
            CategoryTab(
                category = category,
                isSelected = index == selectedCategoryIndex,
                onClick = { onCategorySelected(index) }
            )
        }
    }
}

@Composable
fun CategoryTab(category: Category, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(70))
            .background(if (isSelected) Color(0xFFFAE8A6) else Color.Transparent)
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = category.name,
            style = MaterialTheme.typography.bodyLarge,
            color = if (isSelected) Color(0xFF20D78B) else Color.Gray,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

@Composable
fun SubcategoryProductSection(
    subcategories: List<Subcategory>,
    navController: NavHostController,
    cartViewModel: CartViewModel,
    favoritesViewModel: FavoritesViewModel
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp)
    ) {
        subcategories.forEach { subcategory ->
            item {
                Text(
                    text = subcategory.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            item {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth().height(((subcategory.products.size / 3 + 1) * 140).dp)
                ) {
                    items(subcategory.products) { product ->
                        val isInCart = cartItems.any { it.product.name == product.name }
                        ProductTile(
                            product = product,
                            isInCart = isInCart,
                            cartViewModel = cartViewModel,
                            favoritesViewModel = favoritesViewModel,
                            onProductClick = {
                                navController.navigate(Screen.ProductDetail.createRoute(product.name))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCategoryProductsScreen() {
    val navController = rememberNavController()
    val cartViewModel = CartViewModel()
    val favoritesViewModel = FavoritesViewModel()

    val sampleCategory = Category(
        name = "FOOD CUPBOARD",
        icon = "",
        subcategories = listOf(
            Subcategory(
                name = "Baking Mixes",
                imageUrl = "",
                products = listOf(
                    Product(
                        sku = "123",
                        name = "Cake Mix Carrot",
                        details = "Delicious carrot cake mix",
                        price = 71.99,
                        imageUrl = "https://demo8.1hour.in/media/products/18366.png",
                        type = "GROCERY"
                    ),
                    Product(
                        sku = "124",
                        name = "Vienna Sausage",
                        details = "Tasty sausage",
                        price = 2.4,
                        imageUrl = "https://demo8.1hour.in/media/products/18356.png",
                        type = "GROCERY"
                    )
                )
            )
        )
    )

    CategoryProductsScreen(
        navController = navController,
        categoryName = sampleCategory.name,
        cartViewModel = cartViewModel,
        favoritesViewModel = favoritesViewModel,
        onBackClick = {}
    )
}
