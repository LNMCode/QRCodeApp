package com.ricker.qrcodeapp.presentation.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BackToMainScreen(
    title: String,
    onBackStack: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = title, color = Color.White) },
        backgroundColor = MaterialTheme.colors.primary,
        navigationIcon = {
            IconButton(onClick = { onBackStack() }){
                Icon(Icons.Default.ArrowBack, tint = Color.White, contentDescription = "Icon back")
            }
        }
    )
}