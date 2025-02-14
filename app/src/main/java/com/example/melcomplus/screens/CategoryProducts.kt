//CategoryProducts.kt
package com.example.melcomplus.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.shape.RoundedCornerShape


import com.example.melcomplus.data.CategoryRepository
import com.example.melcomplus.Screen
import com.example.melcomplus.models.Product


@Composable
fun CategoryProductsScreen(navController: NavHostController, categoryName: String) {
    val category = CategoryRepository.categories.find { it.name == categoryName }

    category?.let {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(category.items) { product ->
                ProductTile(product = product, onProductClick = {
                    navController.navigate(Screen.ProductDetail.createRoute(product.name))
                })
            }
        }
    } ?: Text("Category not found", modifier = Modifier.padding(16.dp))
}

@Composable
fun ProductTile(product: Product, onProductClick: (Product) -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(8.dp))
            .clickable { onProductClick(product) }
            .padding(16.dp)
    ) {
        Text(text = product.name, style = MaterialTheme.typography.bodyLarge, color = Color.White)
    }
}
