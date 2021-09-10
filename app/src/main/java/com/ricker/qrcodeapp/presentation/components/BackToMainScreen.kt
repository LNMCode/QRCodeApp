package com.ricker.qrcodeapp.presentation.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ricker.qrcodeapp.presentation.navigation.Screen

@Composable
fun BackToMainScreen(
    title: String,
    onNavigateToMainScreen: (String) -> Unit,
) {
    TopAppBar(
        title = { Text(text = title, color = Color.White) },
        backgroundColor = MaterialTheme.colors.primary,
        navigationIcon = {
            IconButton(onClick = { onNavigateToMainScreen(Screen.QRMain.route) }){
                Icon(Icons.Default.ArrowBack, tint = Color.White, contentDescription = "Icon back")
            }
        }
    )
}