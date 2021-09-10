package com.ricker.qrcodeapp.presentation.ui.qrdetail

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ricker.qrcodeapp.presentation.components.DetailContactItem
import com.ricker.qrcodeapp.presentation.theme.AppTheme
import com.ricker.qrcodeapp.presentation.util.IntentContact

@Composable
fun QRDetailScreen(
    isDarkTheme: Boolean,
    isNetworkAvailable: Boolean,
    onNavigateToMainScreen: (String) -> Unit,
    historyId: String?,
    viewModel: QRDetailViewModel,
    activity: Activity,
) {

    val historyItem = viewModel.historyItem.value
    val findFunction = FindFunction()
    val intentContact = IntentContact(activity)

    if (historyId == null) {
        TODO("Show Invalid History")
    } else {
        viewModel.onTriggerEvent(QRDetailState.GetHistoryById(historyId))
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
                    if (historyItem != null) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 15.dp, top = 15.dp, end = 15.dp, bottom = 0.dp)
                        ) {
                            Text(text = historyItem.value)
                            val detailContacts = findFunction.execute(historyItem.value)
                            for (item in detailContacts) {
                                DetailContactItem(
                                    detailContact = item,
                                    value = historyItem.value,
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