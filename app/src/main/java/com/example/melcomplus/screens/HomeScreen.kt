//package com.example.melcomplus.screens
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import coil.compose.rememberImagePainter
//import com.example.melcomplus.models.Category
//import com.example.melcomplus.models.Product
//import com.example.melcomplus.viewmodels.CartViewModel
//import com.example.melcomplus.components.ProductTile
//
//
//@Composable
//fun HomeScreen(
//    categories: List<Category>, // Pass the category list
//    onCategoryClick: (String) -> Unit, // Pass a navigation callback
//    cartViewModel: CartViewModel,
//    onProductClick: (Product) -> Unit
//) {
//    Column(modifier = Modifier.padding(16.dp)) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text(
//                text = "Categories",
//                style = MaterialTheme.typography.headlineSmall,
//                color = Color.Black
//            )
////            Text(
////                text = "View All",
////                style = MaterialTheme.typography.bodyMedium,
////                color = MaterialTheme.colorScheme.primary,
////                modifier = Modifier.clickable { /* Navigate to all categories */ }
////            )
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(3),
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.spacedBy(8.dp),
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            items(categories.size) { index ->
//                val category = categories[index]
//                CategoryCard(
//                    category = category,
//                    onClick = { onCategoryClick(category.name) }
//                )
//            }
//        }
//
//        // Best Seller Section
//        BestSellerSection(
//            categories = categories,
//            cartViewModel = cartViewModel,
//            onProductClick = onProductClick
//        )
//    }
//}
//
//@Composable
//fun CategoryCard(category: Category, onClick: () -> Unit) {
//    Card(
//        shape = RoundedCornerShape(12.dp),
//        elevation = CardDefaults.cardElevation(8.dp),
//        colors = CardDefaults.cardColors(containerColor = Color(0xFFD9D364)),
//        modifier = Modifier
//            .fillMaxWidth()
//            .aspectRatio(1f) // Ensures a square shape
//            .padding(4.dp)
//            .clickable(onClick = onClick) // Add click behavior to navigate
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier.padding(8.dp)
//        ) {
//            Image(
//                painter = rememberImagePainter(data = category.icon),
//                contentDescription = category.name,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.size(60.dp)
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = category.name,
//                style = MaterialTheme.typography.bodySmall,
//                textAlign = TextAlign.Center,
//                color = Color.Black,
//                fontSize = 14.sp
//            )
//        }
//    }
//}
//
//@Composable
//fun BestSellerSection(
//    categories: List<Category>,
//    cartViewModel: CartViewModel,
//    onProductClick: (Product) -> Unit
//) {
//    // Get the first 2 products from each category
//    val bestSellerProducts = categories.flatMap { it.items.take(2) }
//
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(250.dp)
//            .background(Color.Red)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//            // Best Seller Text
//            Text(
//                text = "Best Seller",
//                style = MaterialTheme.typography.headlineSmall,
//                color = Color.White,
//                modifier = Modifier.padding(bottom = 8.dp)
//            )
//
//            // Horizontal Scrollable Product Tiles
//            LazyRow (
//                horizontalArrangement = Arrangement.spacedBy(8.dp),
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                items(bestSellerProducts) { product ->
//                    ProductTile(
//                        product = product,
//                        cartViewModel = cartViewModel,
//                        onProductClick = onProductClick
//                    )
//                }
//            }
//        }
//    }
//}
//
//


package com.example.melcomplus.screens

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import android.util.Log
import coil.compose.AsyncImage
import com.example.melcomplus.models.Category
import com.example.melcomplus.models.Product
import com.example.melcomplus.viewmodels.CartViewModel
import com.example.melcomplus.components.ProductTile

@Composable
fun HomeScreen(
    categories: List<Category>,
    onCategoryClick: (String) -> Unit,
    cartViewModel: CartViewModel,
    onProductClick: (Product) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Categories",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
//            modifier = Modifier.fillMaxSize(),
//            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories.size) { index ->
                val category = categories[index]
                CategoryCard(
                    category = category,
                    onClick = { onCategoryClick(category.name) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        BestSellerSection(
            categories = categories,
            cartViewModel = cartViewModel,
            onProductClick = onProductClick
        )
    }
}

@Composable
fun CategoryCard(category: Category, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD9D364)),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(4.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = category.icon,
                contentDescription = category.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = category.name,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun BestSellerSection(
    categories: List<Category>,
    cartViewModel: CartViewModel,
    onProductClick: (Product) -> Unit
) {
    val bestSellerProducts = categories.flatMap { it.items.take(2) }
    Log.d("BestSellerSection", "Best Seller Products: ${bestSellerProducts.size}")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.Red)
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(16.dp)
        ) {
            Text(
                text = "Best Seller",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(bestSellerProducts.size) { index ->
                    val product = bestSellerProducts[index]
                    ProductTile(
                        product = product,
                        cartViewModel = cartViewModel,
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

    HomeScreen(
        categories = sampleCategories,
        onCategoryClick = {},
        cartViewModel = CartViewModel(),
        onProductClick = {}
    )
}
