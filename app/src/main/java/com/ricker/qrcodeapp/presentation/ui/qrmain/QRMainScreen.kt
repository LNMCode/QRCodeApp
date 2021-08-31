package com.ricker.qrcodeapp.presentation.ui.qrmain

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.zxing.integration.android.IntentIntegrator
import com.ricker.qrcodeapp.presentation.CustomActivity
import com.ricker.qrcodeapp.presentation.MainActivity
import com.ricker.qrcodeapp.presentation.components.DataItemEvent
import com.ricker.qrcodeapp.presentation.components.ListButtonEvents
import com.ricker.qrcodeapp.presentation.theme.AppTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun QRMainScreen(
    isDarkTheme: Boolean,
    isNetworkAvailable: Boolean,
    viewModel: QRMainViewModel,
    activity: MainActivity,
) {
    val scaffoldState = rememberScaffoldState()

    val enableOptions = viewModel.isEnableOptions.value

    val enableQRCamera = viewModel.isEnableQRCamera.value

    AppTheme(
        darkTheme = isDarkTheme,
        isNetworkAvailable = isNetworkAvailable,
        displayProgressBar = false,
        scaffoldState = scaffoldState,
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            floatingActionButton = {
                Column {
                    ListButtonEvents(
                        modifier = Modifier,
                        listItemCollections = listOf(
                            DataItemEvent.ThreeD
                        ),
                        enable = enableOptions
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
                if (enableQRCamera){
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
            }
        }
    }
}