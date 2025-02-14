////ProductDetialScreen.kt
//package com.example.melcomplus.screens
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.lifecycle.viewmodel.compose.viewModel
//
//import com.example.melcomplus.viewmodels.CartViewModel
//
//@Preview
//@Composable
//fun ProductDetailScreen(productId: String, cartViewModel: CartViewModel = viewModel()) {
//    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
//        Text("Product Detail for: $productId", style = MaterialTheme.typography.headlineSmall)
//        Button(onClick = { cartViewModel.addToCart(productId) }) {
//            Text("Add to Cart")
//        }
//    }
//}


package com.example.melcomplus.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.melcomplus.models.Product
import com.example.melcomplus.viewmodels.CartViewModel

// Sample product data for preview or demo purposes
val sampleProduct = Product(
    name = "Sample Product",
    details = "This is a sample product description.",
    price = 20.0,
    imageUrl = "https://via.placeholder.com/150"
)

@Composable
fun ProductDetailScreen(product: Product, cartViewModel: CartViewModel = viewModel()) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Product Detail: ${product.name}", style = MaterialTheme.typography.headlineSmall)
        Text("Price: \$${product.price}", style = MaterialTheme.typography.bodyLarge)
        Text(product.details, style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { cartViewModel.addToCart(product) }) { // Pass Product object here
            Text("Add to Cart")
        }
    }
}

// Sample Preview
@Preview
@Composable
fun ProductDetailScreenPreview() {
    ProductDetailScreen(product = sampleProduct)
}
