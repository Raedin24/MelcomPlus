package com.example.melcomplus.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.melcomplus.Screen
import com.example.melcomplus.components.ProductTile
import com.example.melcomplus.components.TopNavigationBar
import com.example.melcomplus.models.Product
import com.example.melcomplus.viewmodels.CartViewModel

//@Composable
//fun FavoritesScreen(
//    cartViewModel: CartViewModel,
//    onProductClick: (Product) -> Unit
//) {
//    // Collect the favorites list as state
//    val favorites = cartViewModel.favorites.collectAsState().value
//
//    if (favorites.isEmpty()) {
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            Text("No favorites yet!")
//        }
//    } else {
//        LazyColumn(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            items(favorites) { product ->
//                ProductTile(
//                    product = product,
//                    cartViewModel = cartViewModel,
//                    onProductClick = onProductClick
//                )
//            }
//        }
//    }
//}

@Composable
fun FavoritesScreen(
    cartViewModel: CartViewModel,
//    navController: NavHostController,
    onProductClick: (Product) -> Unit,
    onBackClick: () -> Unit
) {
    val favorites = cartViewModel.favorites.collectAsState().value

    Column(modifier = Modifier.fillMaxSize()) {
        // Add Top Navigation Bar
        TopNavigationBar(
            title = "Favorites",
            onBackClick = onBackClick
        )

        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No favorites yet!")
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize().padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(favorites) { product ->
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
fun FavoritesScreenPreview() {
    val mockCartViewModel = CartViewModel()

    // Add sample products to favorites
    listOf(
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
        ),
        Product(
            name = "COCA-COLA 1.5L",
            details = "Refreshing Coca-Cola 1.5L",
            price = 5.99,
            imageUrl = "https://demo8.1hour.in/media/products/18367.png"
        )
    ).forEach { product ->
        mockCartViewModel.addToFavorites(product)
    }

    FavoritesScreen(
        cartViewModel = mockCartViewModel,
        onProductClick = {},
        onBackClick = {}
    )
}

