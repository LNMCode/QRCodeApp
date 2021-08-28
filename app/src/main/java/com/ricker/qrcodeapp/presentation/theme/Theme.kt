package com.ricker.qrcodeapp.presentation.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ricker.qrcodeapp.presentation.components.ConnectivityMonitor

private val LightThemeColors = lightColors(
    primary = Blue600,
    primaryVariant = Blue400,
    onPrimary = Black2,
    secondary = Color.White,
    secondaryVariant = Teal300,
    onSecondary = Color.Black,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = Grey1,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Black2,
)

private val DarkThemeColors = darkColors(
    primary = Blue700,
    primaryVariant = Color.White,
    onPrimary = Color.White,
    secondary = Black1,
    onSecondary = Color.White,
    error = RedErrorLight,
    background = Color.Black,
    onBackground = Color.White,
    surface = Black1,
    onSurface = Color.White,
)

@Composable
fun AppTheme(
    darkTheme: Boolean,
    isNetworkAvailable: Boolean,
    displayProgressBar: Boolean,
    scaffoldState: ScaffoldState,
    content: @Composable () -> Unit,
){
    MaterialTheme(
        colors = if (darkTheme) DarkThemeColors else LightThemeColors,
        shapes = AppShapes
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Column {
                ConnectivityMonitor(isNetworkAvailable = isNetworkAvailable)
                content()
            }
        }
    }
}