package com.ricker.qrcodeapp.presentation.ui.favorites

import androidx.compose.foundation.ExperimentalFoundationApi
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

@ExperimentalFoundationApi
@Composable
fun FavoriteScreen(
    isDarkTheme: Boolean,
    isNetworkAvailable: Boolean,
    onNavigateToScreen: (String) -> Unit,
    onBackStack: () -> Unit,
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
            topBar = {
                BackToMainScreen(
                    title = "Favorites History",
                    onBackStack = onBackStack
                )
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 15.dp, vertical = 0.dp)
                ) {
                    LazyColumn {
                        itemsIndexed(
                            items = favorites
                        ) { _, history ->
                            HistoryItem(
                                history = history,
                                onNavigateToScreen = onNavigateToScreen
                            )
                        }
                    }
                }
            }
        }
    }
}