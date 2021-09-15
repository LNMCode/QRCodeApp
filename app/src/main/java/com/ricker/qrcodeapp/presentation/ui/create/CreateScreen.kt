package com.ricker.qrcodeapp.presentation.ui.create

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.CoilImage
import com.ricker.qrcodeapp.presentation.MainActivity
import com.ricker.qrcodeapp.presentation.components.BackToMainScreen
import com.ricker.qrcodeapp.presentation.theme.AppTheme
import com.ricker.qrcodeapp.presentation.util.Image
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun CreateScreen(
    isDarkTheme: Boolean,
    isNetworkAvailable: Boolean,
    viewModel: CreateViewModel,
    onSaveImageBitmap: (Bitmap) -> Unit,
    onBackStack: () -> Unit,
) {

    val valueString = viewModel.valueString.value

    val bitmapCreate = viewModel.bitmapCreated.value

    val isEnableCreateQRCode = viewModel.enableCreateQRCode.value

    val scaffoldState = rememberScaffoldState()

    val keyboardController = LocalSoftwareKeyboardController.current

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
                    title = "Create QR Code",
                    onBackStack = onBackStack
                )
            },
        ) {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (bitmapCreate != null && !isEnableCreateQRCode) {
                        CoilImage(
                            data = bitmapCreate,
                            contentDescription = "bitmap",
                            modifier = Modifier.size(350.dp),
                            contentScale = ContentScale.Crop,
                        )
                    }
                    if(isEnableCreateQRCode) {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(.9f)
                                .padding(8.dp),
                            value = valueString,
                            onValueChange = viewModel::onValueStringChange,
                            label = { Text(text = "Enter text") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done,
                            ),
                            textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),
                        )
                        Button(onClick = {
                            if (valueString.isNotBlank()){
                                viewModel.createQRCode()
                                viewModel.isEnableCreateQRCode(false)
                                keyboardController?.hideSoftwareKeyboard()
                            }
                        }) {
                            Text(text = "Create", color = Color.White)
                        }
                    } else {
                        Button(onClick = {
                            viewModel.isEnableCreateQRCode(true)
                            viewModel.clearValueString()
                        }) {
                            Text(text = "Try again", color = Color.White)
                        }
                    }
                    Button(
                        modifier = Modifier.padding(top = 15.dp),
                        onClick = {
                            if (bitmapCreate != null) {
                                onSaveImageBitmap(bitmapCreate)
                            }
                        }) {
                        Text(text = "Save QR Code", color = Color.White)
                    }
                }
            }
        }
    }
}