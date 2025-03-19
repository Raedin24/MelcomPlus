package com.example.melcomplus.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.melcomplus.components.ProductTile
import com.example.melcomplus.components.TopNavigationBar
import com.example.melcomplus.models.Product
import com.example.melcomplus.viewmodels.CartViewModel
import com.example.melcomplus.viewmodels.FavoritesViewModel


@Composable
fun FavoritesScreen(
    cartViewModel: CartViewModel,
    favoritesViewModel: FavoritesViewModel,
    onProductClick: (Product) -> Unit,
    onBackClick: () -> Unit
) {
    val favorites = favoritesViewModel.favorites.collectAsState().value

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
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
                        favoritesViewModel = favoritesViewModel, // Use the correct ViewModel
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
    val mockFavoritesViewModel = FavoritesViewModel()

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
        mockFavoritesViewModel.addToFavorites(product)
    }

    FavoritesScreen(
        cartViewModel = mockCartViewModel,
        favoritesViewModel = mockFavoritesViewModel,
        onProductClick = {},
        onBackClick = {}
    )
}
