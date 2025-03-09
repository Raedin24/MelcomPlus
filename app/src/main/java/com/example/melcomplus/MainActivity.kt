//MainActivity.kt
package com.example.melcomplus



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.melcomplus.data.CategoryRepository
import com.example.melcomplus.screens.CartScreen
import com.example.melcomplus.screens.CategoryProductsScreen
import com.example.melcomplus.screens.HomeScreen
import com.example.melcomplus.screens.ProductDetailScreen
import com.example.melcomplus.screens.SearchScreen
import com.example.melcomplus.viewmodels.CartViewModel


sealed class Screen(val route: String) {
    object Splash : Screen("splash")
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
//        installSplashScreen()
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            MelcomPlusApp()
//            HomeScreen(categories = CategoryRepository.categories)
        }
    }
}


@Composable
fun MelcomPlusApp() {
    val navController = rememberNavController()
    val cartViewModel = remember { CartViewModel() }
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route


    Scaffold(
        bottomBar = {
            if (currentRoute != "splash") {
                BottomNavigationBar(navController)
            }
        }    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(navController, startDestination = Screen.Splash.route) {
                composable(Screen.Splash.route) {
                    com.example.melcomplus.screens.SplashScreen(
                        navController
                    )
                }
                composable(Screen.Home.route) {
                    HomeScreen(
                        categories = CategoryRepository.categories, // Pass the category list
                        onCategoryClick = { categoryName ->
                            navController.navigate(Screen.CategoryProducts.createRoute(categoryName))
                        }
                    )
                }
                composable(Screen.Search.route) { SearchScreen(navController) }
                composable(Screen.Cart.route) { CartScreen(cartViewModel) }
                composable(Screen.ProductDetail.route) { backStackEntry ->
                    val productName = backStackEntry.arguments?.getString("productName") // Change from "productId" to "productName"
                    val product = CategoryRepository.categories
                        .flatMap { it.items }
                        .find { it.name == productName } // Find the product based on its name

                    if (product != null) {
                        ProductDetailScreen(product, cartViewModel)
                    } else {
                        Text("Product not found") // Handle product not found case
                    }
                }
                composable(Screen.CategoryProducts.route) { backStackEntry ->
                    val categoryName = backStackEntry.arguments?.getString("categoryName")
                    if (categoryName != null) {
                        CategoryProductsScreen(
                            navController = navController,
                            categoryName = categoryName, // Pass categoryName
                            cartViewModel = cartViewModel
                        )
                    } else {
                        // Handle case where categoryName is null
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Category name is missing")
                        }
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
                icon = { /* Add an Icon */ } // TODO
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MelcomPlusApp()
}
