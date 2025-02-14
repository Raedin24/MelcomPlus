////ProductDetialScreen.kt

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
//@Preview
//@Composable
//fun ProductDetailScreenPreview() {
//    ProductDetailScreen(product = sampleProduct)
//}
