//SplashScreen.kt

package com.example.melcomplus.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.melcomplus.R


@Composable
fun SplashScreen(navController: NavHostController) {
    Image(
        painter = painterResource(id = R.drawable.splashscreen),
        contentDescription = "Splash Screen",
        contentScale = ContentScale.Crop,  // Ensures the image fills the entire screen without borders
        modifier = Modifier
            .fillMaxSize()  // Fills the screen entirely
    )

    // Navigate to the HomeScreen after 2 seconds
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(1500)
        navController.navigate("home") {
            popUpTo("splash") { inclusive = true } // Remove SplashScreen from backstack
        }
    }
}
