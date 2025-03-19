// components/BottomNavigationBar.kt
package com.example.melcomplus.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.melcomplus.Screen

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        Screen.Home,
        Screen.Search,
        Screen.Favorites,
        Screen.Cart
    )

    NavigationBar {
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
                        is Screen.Cart -> Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                        is Screen.Favorites -> Icon(Icons.Default.Favorite, contentDescription = "Favorites")
                        else -> null
                    }
                }
            )
        }
    }
}