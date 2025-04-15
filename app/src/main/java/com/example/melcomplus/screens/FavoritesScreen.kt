package com.example.melcomplus.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    cartViewModel: CartViewModel,
    favoritesViewModel: FavoritesViewModel,
    onProductClick: (Product) -> Unit,
    onBackClick: () -> Unit
) {
    val favorites = favoritesViewModel.favorites.collectAsState().value
    val cartItems by cartViewModel.cartItems.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(favorites) { product ->
                    val isInCart = cartItems.any { it.product.sku == product.sku }
                    ProductTile(
                        product = product,
                        isInCart = isInCart,
                        cartViewModel = cartViewModel,
                        favoritesViewModel = favoritesViewModel,
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

    // Add sample products with full structure
    listOf(
        Product(
            sku = "178523",
            name = "BETTY CROCKER SUPERMOIST CAKEMIX CARROT 425G",
            details = "Betty Crocker Supermoist Cakemix Carrot 425G",
            price = 71.99,
            imageUrl = "https://demo8.1hour.in/media/products/18366.png",
            type = "GROCERY"
        ),
        Product(
            sku = "183556",
            name = "GOYA VIENNA SAUSAGE 142Gms 46Oz",
            details = "Goya Vienna Sausage 142Gms 46Oz",
            price = 2.40,
            imageUrl = "https://demo8.1hour.in/media/products/18356.png",
            type = "GROCERY"
        ),
        Product(
            sku = "183567",
            name = "COCA-COLA 1.5L",
            details = "Refreshing Coca-Cola 1.5L",
            price = 5.99,
            imageUrl = "https://demo8.1hour.in/media/products/18367.png",
            type = "GROCERY"
        )
    ).forEach {
        mockFavoritesViewModel.addToFavorites(it)
    }

    FavoritesScreen(
        cartViewModel = mockCartViewModel,
        favoritesViewModel = mockFavoritesViewModel,
        onProductClick = {},
        onBackClick = {}
    )
}
