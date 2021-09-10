package com.ricker.qrcodeapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.CoilImage
import com.ricker.qrcodeapp.R
import com.ricker.qrcodeapp.domain.model.History
import com.ricker.qrcodeapp.presentation.navigation.Screen

@Composable
fun HistoryItem(
    history: History,
    onNavigateToScreenDetail: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable {
                onNavigateToScreenDetail(Screen.QRDetail.route + "/${history.id}")
            },
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
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(
                    text = history.value,
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(text = history.scannedDay, style = MaterialTheme.typography.subtitle2)
            }
        }
    }
}