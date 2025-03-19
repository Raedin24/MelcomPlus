//SplashScreen.kt

package com.example.melcomplus.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import com.example.melcomplus.R


@Composable
fun SplashScreen(
    ) {
    Image(
        painter = painterResource(id = R.drawable.splashscreen),
        contentDescription = "Splash Screen",
        contentScale = ContentScale.Crop,  // Ensures the image fills the entire screen without borders
        modifier = Modifier
            .fillMaxSize()  // Fills the screen entirely
            .zIndex(1000f)
    )
}
