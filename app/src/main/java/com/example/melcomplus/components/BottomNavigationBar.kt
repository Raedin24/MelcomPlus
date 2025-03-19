// components/BottomNavigationBar.kt

package com.example.melcomplus.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.melcomplus.Screen
import com.example.melcomplus.models.Product
import com.example.melcomplus.viewmodels.CartViewModel
import com.example.melcomplus.viewmodels.FavoritesViewModel


@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    cartViewModel: CartViewModel,
    favoritesViewModel: FavoritesViewModel
) {
    val items = listOf(
        Screen.Home,
        Screen.Search,
        Screen.Favorites,
        Screen.Cart
    )

    val cartItemCount by cartViewModel.cartItemCount.collectAsState()
    val favoriteItemCount by favoritesViewModel.favoriteItemCount.collectAsState()

    NavigationBar(
        modifier = Modifier
            .zIndex(-1f)
    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = { navController.navigate(screen.route) },
                label = { Text(screen.route) },
                icon = {
                    when (screen) {
                        is Screen.Home -> Icon(Icons.Default.Home, contentDescription = "Home")
                        is Screen.Search -> Icon(Icons.Default.Search, contentDescription = "Search")
                        is Screen.Cart -> BadgedBox(
                            badge = {
                                if (cartItemCount > 0) {
                                    Badge (
                                            containerColor = Color(color = 0xFF46389A),
                                            contentColor = Color.White
                                    ) { Text(cartItemCount.toString()) }
                                }
                            }
                        ) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                        }
                        is Screen.Favorites -> BadgedBox(
                            badge = {
                                if (favoriteItemCount > 0) {
                                    Badge(
                                            containerColor = Color(color = 0xFF46389A),
                                            contentColor = Color.White
                                        ) { Text(favoriteItemCount.toString()) }
                                }
                            }
                        ) {
                            Icon(Icons.Default.Favorite, contentDescription = "Favorites")
                        }
                        else -> null
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    val navController = rememberNavController()
    val cartViewModel = remember { CartViewModel() }
    val favoritesViewModel = remember { FavoritesViewModel() }

    // Mocking some cart and favorite items for the preview
    LaunchedEffect(Unit) {
        cartViewModel.addToCart(Product("Sample Item", "", 10.0, ""))
        favoritesViewModel.addToFavorites(Product("Favorite Item", "", 20.0, ""))
    }

    BottomNavigationBar(
        navController = navController,
        cartViewModel = cartViewModel,
        favoritesViewModel = favoritesViewModel
    )
}
