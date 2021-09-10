package com.ricker.qrcodeapp.presentation.ui.qrdetail

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.coil.CoilImage
import com.ricker.qrcodeapp.R
import com.ricker.qrcodeapp.presentation.components.BackToMainScreen
import com.ricker.qrcodeapp.presentation.components.DetailContact
import com.ricker.qrcodeapp.presentation.components.DetailContactItem
import com.ricker.qrcodeapp.presentation.theme.AppTheme
import com.ricker.qrcodeapp.presentation.util.IntentContact

@Composable
fun QRDetailScreen(
    isDarkTheme: Boolean,
    isNetworkAvailable: Boolean,
    onBackStack: () -> Unit,
    historyId: String?,
    viewModel: QRDetailViewModel,
    activity: Activity,
) {

    val historyItem = viewModel.historyItem.value
    val findFunction = FindFunction()
    val intentContact = IntentContact(activity)
    val isFavorite = viewModel.isFavorite.value

    if (historyId == null) {
        TODO("Show Invalid History")
    } else {
        viewModel.onTriggerEvent(QRDetailState.GetHistoryById(historyId))
        if (historyItem != null) {
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
                            title = "Detail",
                            onBackStack = onBackStack
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            viewModel.onTriggerEvent(
                                QRDetailState.UpdateFavorite(
                                    isFavorite = !isFavorite,
                                    idHistoryItem = historyId,
                                )
                            )
                        }) {
                            CoilImage(
                                data = if (isFavorite) R.drawable.ic_star_filled else R.drawable.ic_star_not_filled,
                                contentDescription = "Icon detail",
                                modifier = Modifier.size(40.dp),
                                contentScale = ContentScale.Crop,
                            )
                        }
                    }
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 15.dp, top = 15.dp, end = 15.dp, bottom = 0.dp)
                        ) {
                            Text(text = historyItem.value, fontSize = 18.sp)
                            DetailContactItem(
                                detailContact = DetailContact.SaveToClipBoard,
                                value = historyItem.value,
                                intentContact = intentContact::execute
                            )
                            historyItem.value.split("\n").map { value ->
                                findFunction.execute(value).map { item ->
                                    DetailContactItem(
                                        detailContact = item,
                                        value = value,
                                        intentContact = intentContact::execute
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}