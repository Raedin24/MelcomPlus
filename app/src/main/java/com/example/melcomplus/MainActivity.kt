package com.example.melcomplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.melcomplus.data.CategoryRepository
import com.example.melcomplus.screens.*
import com.example.melcomplus.viewmodels.CartViewModel
import com.example.melcomplus.viewmodels.FavoritesViewModel
import com.example.melcomplus.components.BottomNavigationBar
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.melcomplus.data.CsvProcessor


sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Selection : Screen("selection")
    object Home : Screen("home?type={type}") {
        fun createRoute(type: String) = "home?type=$type"
    }
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

//        // Load CSV data from assets
//        val productStream = assets.open("products_listing.csv")
//        val categoryStream = assets.open("catalog_categories.csv")
//        val subcategoryStream = assets.open("catalog_subcategories.csv")
//
//        CsvProcessor.loadData(
//            productCsv = productStream,
//            categoryCsv = categoryStream,
//            subcategoryCsv = subcategoryStream
//        )

        // Centralized CSV loading
        CategoryRepository.loadFromAssets(this)

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

    // ✅ Save the selected type across recompositions
    var selectedType by rememberSaveable { mutableStateOf("GROCERY") }

    val currentRoute by navController.currentBackStackEntryAsState()
    val showBottomBar = currentRoute?.destination?.route !in listOf(Screen.Splash.route, Screen.Selection.route)

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(
                    navController = navController,
                    cartViewModel = cartViewModel,
                    favoritesViewModel = favoritesViewModel
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavigationGraph(
                navController = navController,
                cartViewModel = cartViewModel,
                favoritesViewModel = favoritesViewModel,
                selectedType = selectedType,
                onUpdateType = { selectedType = it }
            )
        }
    }
}


@Composable
fun NavigationGraph(
    navController: NavHostController,
    cartViewModel: CartViewModel,
    favoritesViewModel: FavoritesViewModel,
    selectedType: String,
    onUpdateType: (String) -> Unit
) {
    NavHost(navController, startDestination = Screen.Splash.route) {

        composable(Screen.Splash.route) {
            SplashScreen()
            LaunchedEffect(Unit) {
                kotlinx.coroutines.delay(1500)
                navController.navigate(Screen.Selection.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            }
        }

        composable(Screen.Selection.route) {
            SelectionScreen(
                navController = navController,
                onSelectType = { type ->
                    onUpdateType(type)
                    navController.navigate(Screen.Home.createRoute(type))
                }
            )
        }

        composable(Screen.Home.route) { backStackEntry ->
//            val selectedType = backStackEntry.arguments?.getString("type")?.uppercase() ?: "GROCERY"

            val context = LocalContext.current

            // ✅ Force recomposition when this screen becomes visible again
            val currentBackStackEntry by rememberUpdatedState(backStackEntry)

            // ✅ Ensure latest categories are always pulled when navigating back
            val allCategories by remember {
                derivedStateOf {
                    if (CategoryRepository.categories.isEmpty()) {
                        CategoryRepository.loadFromAssets(context)
                    }
                    CategoryRepository.categories
                }
            }

            val filteredCategories = remember(allCategories, selectedType) {
                allCategories.filter { category ->
                    category.subcategories.any { sub ->
                        sub.products.any { it.type.equals(selectedType, ignoreCase = true) }
                    }
                }.map { category ->
                    category.copy(
                        subcategories = category.subcategories.map { sub ->
                            sub.copy(products = sub.products.filter {
                                it.type.equals(selectedType, ignoreCase = true)
                            })
                        }.filter { it.products.isNotEmpty() }
                    )
                }.filter { it.subcategories.isNotEmpty() }
            }

            HomeScreen(
                categories = filteredCategories,
                cartViewModel = cartViewModel,
                favoritesViewModel = favoritesViewModel,
                onCategoryClick = { categoryName ->
                    navController.navigate(Screen.CategoryProducts.createRoute(categoryName))
                },
                onProductClick = { product ->
                    navController.navigate(Screen.ProductDetail.createRoute(product.name))
                },
                onBackClick = { navController.navigate(Screen.Selection.route) }
            )
        }

        composable(Screen.Search.route) {
            SearchScreen(
                navController = navController,
                selectedType = selectedType,
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
                .flatMap { it.subcategories }
                .flatMap { it.products }
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MelcomPlusApp()
}
