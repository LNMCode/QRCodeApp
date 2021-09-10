package com.ricker.qrcodeapp.presentation.ui.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ricker.qrcodeapp.presentation.components.BackToMainScreen
import com.ricker.qrcodeapp.presentation.components.HistoryItem
import com.ricker.qrcodeapp.presentation.theme.AppTheme

@Composable
fun FavoriteScreen(
    isDarkTheme: Boolean,
    isNetworkAvailable: Boolean,
    onNavigateToScreen: (String) -> Unit,
    viewModel: FavoriteViewModel,
) {
    val favorites = viewModel.favorites.value

    val scaffoldState = rememberScaffoldState()

    AppTheme(
        darkTheme = isDarkTheme,
        isNetworkAvailable = isNetworkAvailable,
        displayProgressBar = false,
        scaffoldState = scaffoldState,
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    BackToMainScreen(
                        title = "Favorites History",
                        onNavigateToMainScreen = onNavigateToScreen
                    )
                    LazyColumn(
                        modifier = Modifier.padding(
                            start = 15.dp,
                            top = 15.dp,
                            end = 15.dp,
                            bottom = 0.dp
                        )
                    ) {
                        itemsIndexed(
                            items = favorites
                        ) { _, history ->
                            HistoryItem(
                                history = history,
                                onNavigateToScreenDetail = onNavigateToScreen
                            )
                        }
                    }
                }
            }
        }
    }
}