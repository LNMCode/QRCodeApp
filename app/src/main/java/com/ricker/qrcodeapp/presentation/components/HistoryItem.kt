package com.ricker.qrcodeapp.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.CoilImage
import com.ricker.qrcodeapp.R
import com.ricker.qrcodeapp.domain.model.History
import com.ricker.qrcodeapp.presentation.navigation.Screen

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun HistoryItem(
    history: History,
    enableRemoveItems: Boolean,
    onLongClick: (Boolean) -> Unit,
    onAppendRemoveItem: (String) -> Unit,
    onRemoveItem: (String) -> Unit,
    onNavigateToScreen: (String) -> Unit,
) {
    val checkedRemove = rememberSaveable {
        mutableStateOf(false)
    }
    if (!enableRemoveItems) checkedRemove.value = false
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .combinedClickable(
                onClick = {
                    if (enableRemoveItems) {
                        checkedRemove.value = !checkedRemove.value
                        if (checkedRemove.value) {
                            onAppendRemoveItem(history.id)
                        } else {
                            onRemoveItem(history.id)
                        }
                    } else {
                        onNavigateToScreen(Screen.QRDetail.route + "/${history.id}")
                    }
                },
                onLongClick = {
                    onLongClick(true)
                },
            ),
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            modifier = Modifier
                .padding(15.dp),
        ) {
            CoilImage(
                data = R.drawable.ic_history,
                contentDescription = "Icon history",
                modifier = Modifier.size(50.dp),
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(.85f)
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = history.value,
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(text = history.scannedDay, style = MaterialTheme.typography.subtitle2)
            }
            AnimatedVisibility(
                visible = enableRemoveItems,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                CoilImage(
                    data = if (checkedRemove.value) R.drawable.ic_checked else R.drawable.ic_not_checked,
                    contentDescription = "Icon history",
                    modifier = Modifier.size(20.dp),
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}