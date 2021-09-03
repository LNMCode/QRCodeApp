package com.ricker.qrcodeapp.presentation.ui.qrdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ricker.qrcodeapp.presentation.theme.AppTheme

@Composable
fun QRDetailScreen(
    isDarkTheme: Boolean,
    isNetworkAvailable: Boolean,
    onNavigateToMainScreen: (String) -> Unit,
    historyId: String?,
    viewModel: QRDetailViewModel,
) {
    if (historyId == null) {
        TODO("Show Invalid History")
    } else {
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
                    Text(text = "detail $historyId")
                }
            }
        }
    }
}