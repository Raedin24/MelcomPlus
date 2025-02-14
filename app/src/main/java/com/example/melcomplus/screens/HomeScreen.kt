package com.example.melcomplus.screens

//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import coil.compose.rememberImagePainter
//import androidx.compose.ui.layout.ContentScale
//
//import com.example.melcomplus.models.Product
//
//@Composable
//fun HomeScreen(products: List<Product>, onAddToCart: (Product) -> Unit) {
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(3),
//        modifier = Modifier.fillMaxSize()
//    ) {
//        items(products.size) { index ->
//            ProductTile(product = products[index], onAddToCart = onAddToCart)
//        }
//    }
//}
//
//@Composable
//fun ProductTile(product: Product, onAddToCart: (Product) -> Unit) {
//    Card(
//        modifier = Modifier
//            .padding(8.dp)
//            .fillMaxWidth()
//            .height(250.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Corrected this line
//    ) {
//        Column(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            // Image
//            Image(
//                painter = rememberImagePainter(product.imageUrl),
//                contentDescription = product.name,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(1f),
//                contentScale = ContentScale.Crop
//            )
//
//            // Product details
//            Column(
//                modifier = Modifier
//                    .padding(8.dp)
//                    .weight(1f)
//            ) {
//                Text(text = product.name, fontWeight = FontWeight.Bold)
//                Spacer(modifier = Modifier.height(4.dp))
//                Text(text = "\$${product.price}", color = Color.Gray)
//                Spacer(modifier = Modifier.height(8.dp))
//
//                // Add to Cart button
//                Button(
//                    onClick = { onAddToCart(product) },
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text("Add to Cart")
//                }
//            }
//        }
//    }
//}


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

import com.example.melcomplus.data.CategoryRepository
import com.example.melcomplus.models.Category
import com.example.melcomplus.models.Product
import com.example.melcomplus.ui.theme.yellowCat

//import com.example.melcomplus.ui.theme.

@Composable
fun HomeScreen() {
    val categories = CategoryRepository.categories
    val featuredProducts = categories.flatMap { it.items }.take(5) // Sample 5 featured products

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Greeting Section
        Text(
            text = "Hi Kofi!",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Horizontal Featured Products
        Text(
            text = "Recommended Products",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(featuredProducts.size) { index ->
                ProductCard(product = featuredProducts[index])
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Categories Section
        Text(
            text = "Categories",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(3), // 3 columns
            contentPadding = PaddingValues(4.dp)
        ) {
            items(categories.size) { index ->
                CategoryCard(category = categories[index])
            }
        }
    }
}

@Composable
fun ProductCard(product: Product) {
    Box(
        modifier = Modifier
            .size(120.dp)
            .background(Color.LightGray, shape = MaterialTheme.shapes.medium)
            .clickable { /* Navigate to product details */ },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = rememberImagePainter(data = product.imageUrl),
            contentDescription = product.name,
            modifier = Modifier.size(80.dp)
        )
    }
}

@Composable
fun CategoryCard(category: Category, backgroundColor: Color = yellowCat) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .clickable { /* Handle category click */ }
            .fillMaxWidth()
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = rememberImagePainter(data = category.icon),
                contentDescription = category.name,
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = category.name,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}
