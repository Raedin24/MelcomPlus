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
import androidx.compose.runtime.*
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
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route?.substringBefore("?")

    NavigationBar(
        modifier = Modifier.zIndex(1f)
    ) {
        items.forEach { screen ->
            val label = when (screen) {
                is Screen.Home -> "Home"
                is Screen.Search -> "Search"
                is Screen.Favorites -> "Favorites"
                is Screen.Cart -> "Cart"
                else -> ""
            }

            val icon = when (screen) {
                is Screen.Home -> Icons.Default.Home
                is Screen.Search -> Icons.Default.Search
                is Screen.Favorites -> Icons.Default.Favorite
                is Screen.Cart -> Icons.Default.ShoppingCart
                else -> null
            }

            NavigationBarItem(
                selected = currentRoute == screen.route.substringBefore("?"),
                onClick = {
                    if (currentRoute != screen.route.substringBefore("?")) {
                        navController.navigate(screen.route)
                    }
                },
                label = { Text(label) },
                icon = {
                    if (screen is Screen.Cart) {
                        BadgedBox(
                            badge = {
                                if (cartItemCount > 0) {
                                    Badge(
                                        containerColor = Color(0xFF46389A),
                                        contentColor = Color.White
                                    ) {
                                        Text(cartItemCount.toString())
                                    }
                                }
                            }
                        ) {
                            Icon(icon!!, contentDescription = label)
                        }
                    } else if (screen is Screen.Favorites) {
                        BadgedBox(
                            badge = {
                                if (favoriteItemCount > 0) {
                                    Badge(
                                        containerColor = Color(0xFF46389A),
                                        contentColor = Color.White
                                    ) {
                                        Text(favoriteItemCount.toString())
                                    }
                                }
                            }
                        ) {
                            Icon(icon!!, contentDescription = label)
                        }
                    } else {
                        Icon(icon!!, contentDescription = label)
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

    // Populate mock data
    LaunchedEffect(Unit) {
        cartViewModel.addToCart(
            Product("001", "Sample Item", "Test Details", 10.0, "", "GROCERY")
        )
        favoritesViewModel.addToFavorites(
            Product("002", "Favorite Item", "Test Details", 20.0, "", "GROCERY")
        )
    }

    BottomNavigationBar(
        navController = navController,
        cartViewModel = cartViewModel,
        favoritesViewModel = favoritesViewModel
    )
}
