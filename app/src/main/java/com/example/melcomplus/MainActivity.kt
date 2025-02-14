//MainActivity.kt
package com.example.melcomplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.material3.*
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen


import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.melcomplus.viewmodels.CartViewModel
import com.example.melcomplus.screens.CategoryProductsScreen



sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search")
    object ProductDetail : Screen("productDetail/{productId}") {
        fun createRoute(productId: String) = "productDetail/$productId"
    }
    object Cart : Screen("cart")
    object CategoryProducts : Screen("categoryProducts/{categoryName}") {
        fun createRoute(categoryName: String) = "categoryProducts/$categoryName"
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            MelcomPlusApp()
        }
    }
}


@Composable
fun MelcomPlusApp() {
    val navController = rememberNavController()
    val cartViewModel = remember { CartViewModel() }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(navController, startDestination = Screen.Home.route) {
                composable(Screen.Home.route) { HomeScreen(navController) }
                composable(Screen.Search.route) { SearchScreen() }
                composable(Screen.Cart.route) { CartScreen() }
                composable(Screen.ProductDetail.route) { backStackEntry ->
                    val productId = backStackEntry.arguments?.getString("productId")
                    ProductDetailScreen(productId ?: "")
                }
                composable(Screen.CategoryProducts.route) { backStackEntry ->
                    val categoryName = backStackEntry.arguments?.getString("categoryName")
                    if (categoryName != null) {
                        CategoryProductsScreen(navController, categoryName)
                    }
                }
            }
        }
    }
}



@Composable
fun HomeScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Home Screen", style = MaterialTheme.typography.headlineSmall)
        Button(onClick = { navController.navigate(Screen.Search.route) }) {
            Text("Go to Search")
        }
        Button(onClick = { navController.navigate(Screen.Cart.route) }) {
            Text("Go to Cart")
        }
    }
}

@Composable
fun SearchScreen() {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Search Screen", style = MaterialTheme.typography.headlineSmall)
    }
}

@Composable
fun ProductDetailScreen(productId: String) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Product Detail for ID: $productId", style = MaterialTheme.typography.headlineSmall)
    }
}

@Composable
fun CartScreen() {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Cart Screen", style = MaterialTheme.typography.headlineSmall)
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        Screen.Home,
        Screen.Search,
        Screen.Cart
    )

    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = { navController.navigate(screen.route) },
                label = { Text(screen.route) },
                icon = { /* Add an Icon if needed */ } // TODO
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MelcomPlusApp()
}
