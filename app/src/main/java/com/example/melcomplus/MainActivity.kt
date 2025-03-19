// MainActivity.kt

package com.example.melcomplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.melcomplus.components.BottomNavigationBar
import com.example.melcomplus.data.CategoryRepository
import com.example.melcomplus.screens.CartScreen
import com.example.melcomplus.screens.CategoryProductsScreen
import com.example.melcomplus.screens.FavoritesScreen
import com.example.melcomplus.screens.HomeScreen
import com.example.melcomplus.screens.ProductDetailScreen
import com.example.melcomplus.screens.SearchScreen
import com.example.melcomplus.screens.SplashScreen
import com.example.melcomplus.viewmodels.CartViewModel
import com.example.melcomplus.viewmodels.FavoritesViewModel

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Search : Screen("search")
    object Favorites : Screen("favorites")
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
    val favoritesViewModel = remember { FavoritesViewModel() }
    val isSplashScreenVisible = remember { mutableStateOf(true) }

        Scaffold(
            bottomBar = {
                if (!isSplashScreenVisible.value) {
                    BottomNavigationBar(navController, cartViewModel, favoritesViewModel)
                }
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                NavigationGraph(
                    navController,
                    cartViewModel,
                    favoritesViewModel,
                    isSplashScreenVisible = isSplashScreenVisible
                )
            }
        }
    }


@Composable
fun NavigationGraph(
    navController: NavHostController,
    cartViewModel: CartViewModel,
    favoritesViewModel: FavoritesViewModel,
    isSplashScreenVisible: MutableState<Boolean>
) {
    val currentRoute by navController.currentBackStackEntryAsState()

    // Hide splash screen when navigating away from it
    LaunchedEffect(currentRoute?.destination?.route) {
        if (currentRoute?.destination?.route == Screen.Splash.route) {
            kotlinx.coroutines.delay(1500)
            navController.navigate(Screen.Home.route){
                popUpTo(Screen.Splash.route) {inclusive = false}
            }
            isSplashScreenVisible.value = false

        }
    }

    NavHost(navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen()
        }
        composable(Screen.Home.route) {
            HomeScreen(
                categories = CategoryRepository.categories,
                onCategoryClick = { categoryName ->
                    navController.navigate(Screen.CategoryProducts.createRoute(categoryName))
                },
                cartViewModel = cartViewModel,
                favoritesViewModel = favoritesViewModel,
                onProductClick = { product ->
                    navController.navigate(Screen.ProductDetail.createRoute(product.name))
                },
            )
        }
        composable(Screen.Search.route) {
            SearchScreen(
                navController = navController,
                categories = CategoryRepository.categories,
                cartViewModel = cartViewModel,
                favoritesViewModel = favoritesViewModel
            )
        }
        composable(Screen.Cart.route) {
            CartScreen(
                cartViewModel = cartViewModel,
                navController = navController,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.ProductDetail.route) { backStackEntry ->
            val productName = backStackEntry.arguments?.getString("productName")
            val product = CategoryRepository.categories
                .flatMap { it.items }
                .find { it.name == productName }

            if (product != null) {
                ProductDetailScreen(
                    product = product,
                    cartViewModel = cartViewModel,
                    onBackClick = { navController.popBackStack() }
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Product not found")
                }
            }
        }
        composable(Screen.CategoryProducts.route) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName")
            if (categoryName != null) {
                CategoryProductsScreen(
                    navController = navController,
                    categoryName = categoryName,
                    cartViewModel = cartViewModel,
                    favoritesViewModel = favoritesViewModel,
                    onBackClick = { navController.popBackStack() }
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Category name is missing")
                }
            }
        }
        composable(Screen.Favorites.route) {
            FavoritesScreen(
                cartViewModel = cartViewModel,
                favoritesViewModel = favoritesViewModel,
                onProductClick = { product ->
                    navController.navigate(Screen.ProductDetail.createRoute(product.name))
                },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

// Helper function to get current route
@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MelcomPlusApp()
}
