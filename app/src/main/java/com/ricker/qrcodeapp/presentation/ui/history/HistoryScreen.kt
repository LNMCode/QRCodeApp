package com.ricker.qrcodeapp.presentation.ui.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ricker.qrcodeapp.presentation.theme.AppTheme

@Composable
fun HistoryScreen(
    isDarkTheme: Boolean,
    isNetworkAvailable: Boolean,
    viewModel: HistoryViewModel,
) {

    val scaffoldState = rememberScaffoldState()

    AppTheme(
        darkTheme = isDarkTheme,
        isNetworkAvailable = isNetworkAvailable,
        displayProgressBar = false,
        scaffoldState = scaffoldState,
    ) {
        Scaffold(
            scaffoldState = scaffoldState
        ) {
            Box(modifier = Modifier.fillMaxSize()) {

            }
        }
    }
}