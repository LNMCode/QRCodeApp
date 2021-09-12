package com.ricker.qrcodeapp.presentation.ui.qrmain

import android.app.Activity
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.CoilImage
import com.google.zxing.integration.android.IntentIntegrator
import com.ricker.qrcodeapp.R
import com.ricker.qrcodeapp.presentation.CustomActivity
import com.ricker.qrcodeapp.presentation.MainActivity
import com.ricker.qrcodeapp.presentation.components.HistoryItem
import com.ricker.qrcodeapp.presentation.components.ListButtonEvents
import com.ricker.qrcodeapp.presentation.components.listItemEvent
import com.ricker.qrcodeapp.presentation.theme.AppTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@Composable
fun QRMainScreen(
    isDarkTheme: Boolean,
    isNetworkAvailable: Boolean,
    onNavigateToScreen: (String) -> Unit,
    viewModel: QRMainViewModel,
    activity: MainActivity,
) {
    val scaffoldState = rememberScaffoldState()

    val enableOptions = viewModel.isEnableOptions.value

    val enableQRCamera = viewModel.isEnableQRCamera.value

    val enableRemoveItems = viewModel.isEnableRemoveItems.value

    val historyItems = viewModel.historyItems.value

    val numberRemoveItems = viewModel.numberRemoveItems.value

    val loading = viewModel.loading.value

    AppTheme(
        darkTheme = isDarkTheme,
        isNetworkAvailable = isNetworkAvailable,
        displayProgressBar = false,
        scaffoldState = scaffoldState,
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                AnimatedVisibility(
                    visible = enableRemoveItems,
                    enter = slideInVertically() + expandVertically() + fadeIn(),
                    exit = slideOutVertically() + shrinkVertically() + fadeOut()
                ) {
                    TopAppBar(
                        title = { Text(text = "$numberRemoveItems", color = Color.White) },
                        elevation = 10.dp,
                        navigationIcon = {
                            IconButton(onClick = viewModel::clearListItemsRemove) {
                                Icon(
                                    Icons.Default.ArrowBack,
                                    tint = Color.White,
                                    contentDescription = "Icon back"
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = { viewModel.onTriggerEvent(QRMainState.DeleteHistory) }) {
                                CoilImage(
                                    data = R.drawable.ic_trash,
                                    contentDescription = "Icon trash",
                                    modifier = Modifier.size(25.dp),
                                    contentScale = ContentScale.Crop,
                                )
                            }
                        }
                    )
                }
            },
            floatingActionButton = {
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    ListButtonEvents(
                        modifier = Modifier,
                        listItemCollections = listItemEvent,
                        enable = enableOptions,
                        enableQRCamera = viewModel::setEnableQRCamera,
                        onNavigateToScreen = onNavigateToScreen,
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
                        .padding(horizontal = 15.dp, vertical = 0.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 15.dp),
                        text = "Scanning History",
                        style = MaterialTheme.typography.h6
                    )
                    for (history in historyItems) {
                        HistoryItem(
                            history = history,
                            enableRemoveItems = enableRemoveItems,
                            onLongClick = viewModel::setEnableRemoveItem,
                            onAppendRemoveItem = viewModel::appendRemoveItem,
                            onRemoveItem = viewModel::removeItem,
                            onNavigateToScreen = onNavigateToScreen
                        )
                    }
                }
                AnimatedVisibility(visible = enableOptions, modifier = Modifier.fillMaxSize()) {
                    Spacer(modifier = Modifier
                        .fillMaxSize()
                        .clickable { viewModel.setEnableOptions(false) }
                        .background(Color.Black.copy(alpha = .6f)))
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