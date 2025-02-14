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
import com.example.melcomplus.screens.*
import com.example.melcomplus.data.CategoryRepository



//sealed class Screen(val route: String) {
//    object Home : Screen("home")
//    object Search : Screen("search")
//    object ProductDetail : Screen("productDetail/{productId}") {
//        fun createRoute(productId: String) = "productDetail/$productId"
//    }
//    object Cart : Screen("cart")
//    object CategoryProducts : Screen("categoryProducts/{categoryName}") {
//        fun createRoute(categoryName: String) = "categoryProducts/$categoryName"
//    }
//}


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search")
    object Cart : Screen("cart")
    object CategoryProducts : Screen("categoryProducts/{categoryName}") {
        fun createRoute(categoryName: String) = "categoryProducts/$categoryName"
    }
    object ProductDetail : Screen("productDetail/{productName}") {
        fun createRoute(productName: String) = "productDetail/$productName"
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
                composable(Screen.Home.route) {
                    HomeScreen { categoryName ->
                        navController.navigate(Screen.CategoryProducts.createRoute(categoryName))
                    }
                }
                composable(Screen.Search.route) { SearchScreen(navController) }
                composable(Screen.Cart.route) { CartScreen(cartViewModel) }
                composable(Screen.ProductDetail.route) { backStackEntry ->
                    val productName = backStackEntry.arguments?.getString("productName") // Change from "productId" to "productName"
                    val product = CategoryRepository.categories
                        .flatMap { it.items }
                        .find { it.name == productName } // Find the product based on its name

                    if (product != null) {
                        ProductDetailScreen(product)
                    } else {
                        Text("Product not found") // Handle product not found case
                    }
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
