//package com.example.melcomplus.screens
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.navigation.NavHostController
//import kotlinx.coroutines.delay
////import android.content.Intent
////import android.os.Bundle
////import android.os.Handler
////import android.view.WindowManager
////import androidx.compose.foundation.Image
//
////import androidx.appcompat.app.AppCompatActivity
//
//@Preview
//@Composable
//fun SplashScreen(navController: NavHostController) {
//    LaunchedEffect(Unit) {
//        delay(2000)
//        navController.navigate("home")
//    }
//    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        Text("Melcom Plus", style = MaterialTheme.typography.headlineMedium)
//
//    }
//}


package com.example.melcomplus.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.melcomplus.R
import kotlinx.coroutines.delay
import androidx.compose.ui.layout.ContentScale


//@Composable
//fun SplashScreen(navController: NavHostController) {
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = Modifier.fillMaxSize()
//    ) {
//        // Display a full-screen image
//        Image(
//            painter = painterResource(id = R.drawable.splashscreen),
//            contentDescription = "Splash Screen"
//        )
//    }
//
//    // Navigate to the HomeScreen after 2 seconds
//    LaunchedEffect(Unit) {
//        delay(2000)
//        navController.navigate("home") {
//            popUpTo("splash") { inclusive = true } // Remove SplashScreen from backstack
//        }
//    }
//}


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
        kotlinx.coroutines.delay(2000)
        navController.navigate("home") {
            popUpTo("splash") { inclusive = true } // Remove SplashScreen from backstack
        }
    }
}
