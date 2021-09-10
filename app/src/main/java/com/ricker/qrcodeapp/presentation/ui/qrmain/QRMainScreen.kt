package com.ricker.qrcodeapp.presentation.ui.qrmain

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.zxing.integration.android.IntentIntegrator
import com.ricker.qrcodeapp.presentation.CustomActivity
import com.ricker.qrcodeapp.presentation.MainActivity
import com.ricker.qrcodeapp.presentation.components.HistoryItem
import com.ricker.qrcodeapp.presentation.components.ListButtonEvents
import com.ricker.qrcodeapp.presentation.components.listItemEvent
import com.ricker.qrcodeapp.presentation.theme.AppTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun QRMainScreen(
    isDarkTheme: Boolean,
    isNetworkAvailable: Boolean,
    onNavigateToScreenDetail: (String) -> Unit,
    viewModel: QRMainViewModel,
    activity: MainActivity,
) {
    val scaffoldState = rememberScaffoldState()

    val enableOptions = viewModel.isEnableOptions.value

    val enableQRCamera = viewModel.isEnableQRCamera.value

    val historyItems = viewModel.historyItems.value

    val loading = viewModel.loading.value

    AppTheme(
        darkTheme = isDarkTheme,
        isNetworkAvailable = isNetworkAvailable,
        displayProgressBar = false,
        scaffoldState = scaffoldState,
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            floatingActionButton = {
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    ListButtonEvents(
                        modifier = Modifier,
                        listItemCollections = listItemEvent,
                        enable = enableOptions,
                        enableQRCamera = viewModel::setEnableQRCamera
                    )
                    ExtendedFloatingActionButton(
                        text = { Text(text = "Tùy chọn") },
                        onClick = { viewModel.setEnableOptions(!enableOptions) })
                }
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                if (enableQRCamera) {
                    initiateScan(activity)
                    viewModel.setEnableQRCamera(false)
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 15.dp, top = 15.dp, end = 15.dp, bottom = 0.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 8.dp),
                        text = "Scanning History",
                        style = MaterialTheme.typography.h6
                    )
                    LazyColumn {
                        itemsIndexed(
                            items = historyItems
                        ) { _, history ->
                            HistoryItem(
                                history = history,
                                onNavigateToScreenDetail = onNavigateToScreenDetail
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun initiateScan(activity: Activity) {
    IntentIntegrator(activity).apply {
        captureActivity = CustomActivity::class.java
        setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        setPrompt("Scan a barcode")
        setCameraId(0)
        setOrientationLocked(false)
        setBeepEnabled(false)
        initiateScan()
    }
}