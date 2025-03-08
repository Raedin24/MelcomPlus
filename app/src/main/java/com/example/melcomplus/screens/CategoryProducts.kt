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

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter


import com.example.melcomplus.data.CategoryRepository
import com.example.melcomplus.Screen
import com.example.melcomplus.models.Product
import com.example.melcomplus.viewmodels.CartViewModel


//@Composable
//fun CategoryProductsScreen(navController: NavHostController, categoryName: String) {
//    val category = CategoryRepository.categories.find { it.name == categoryName }
//
//    category?.let {
//        LazyColumn(modifier = Modifier.fillMaxSize()) {
//            items(category.items) { product ->
//                ProductTile(product = product, onProductClick = {
//                    navController.navigate(Screen.ProductDetail.createRoute(product.name))
//                })
//            }
//        }
//    } ?: Text("Category not found", modifier = Modifier.padding(16.dp))
//}
//
//@Composable
//fun ProductTile(product: Product, onProductClick: (Product) -> Unit) {
//    Box(
//        modifier = Modifier
//            .padding(8.dp)
//            .fillMaxWidth()
//            .background(MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(8.dp))
//            .clickable { onProductClick(product) }
//            .padding(16.dp)
//    ) {
//        Text(text = product.name, style = MaterialTheme.typography.bodyLarge, color = Color.White)
//    }
//}


@Composable
fun CategoryProductsScreen(
    navController: NavHostController,
    categoryName: String,
    cartViewModel: CartViewModel
) {
    val category = CategoryRepository.categories.find { it.name == categoryName }

    category?.let {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(category.items) { product ->
                ProductTile(
                    product = product,
                    onProductClick = {
                        navController.navigate(Screen.ProductDetail.createRoute(product.name))
                    },
                    onAddToCart = { cartViewModel.addToCart(product) }
                )
            }
        }
    } ?: Text("Category not found", modifier = Modifier.padding(16.dp))
}

@Composable
fun ProductTile(product: Product, onProductClick: (Product) -> Unit, onAddToCart: (Product) -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f) // Keeps the card square
            .clickable { onProductClick(product) }
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            // Product Image
            Image(
                painter = rememberAsyncImagePainter(model = product.imageUrl),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp), // Set a fixed height for the image
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))

            // "Add" Button
            Button(
                onClick = { onAddToCart(product) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Add")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Product Name
            Text(
                text = product.name,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black
            )

            // Product Price
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}
