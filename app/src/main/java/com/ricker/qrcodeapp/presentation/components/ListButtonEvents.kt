package com.ricker.qrcodeapp.presentation.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.CoilImage
import com.ricker.qrcodeapp.presentation.navigation.Screen
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@Composable
fun ListButtonEvents(
    modifier: Modifier,
    listItemCollections: List<DataItemEvent>,
    enable: Boolean,
    enableQRCamera: (Boolean) -> Unit,
    onNavigateToScreen: (String) -> Unit,
) {
    BoxWithConstraints(modifier = modifier) {
        val offset by animateDpAsState(
            if (enable) 0.dp else maxWidth,
            animationSpec = tween(500)
        )
        Column(
            modifier = Modifier
                .offset(x = offset)
                .align(Alignment.BottomEnd)
        ) {
            listItemCollections.forEach { circleButton ->
                Row(
                    modifier = Modifier
                        .padding(vertical = 6.dp)
                        .align(Alignment.End)
                ) {
                    Surface(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        shape = MaterialTheme.shapes.medium,
                    ) {
                        Text(
                            modifier = Modifier
                                .background(Color.White)
                                .padding(horizontal = 10.dp, vertical = 2.dp),
                            text = circleButton.name,
                            style = MaterialTheme.typography.button,
                            maxLines = 1,
                        )
                    }
                    Spacer(modifier = Modifier.size(width = 8.dp, height = 0.dp))
                    FloatingActionButton(
                        modifier = Modifier.size(50.dp),
                        backgroundColor = Color.White,
                        elevation = FloatingActionButtonDefaults.elevation(2.dp),
                        onClick = {
                            when(circleButton){
                                DataItemEvent.ScanCode -> enableQRCamera(true)
                                DataItemEvent.Favorites -> { onNavigateToScreen(Screen.Favorites.route) }
                                DataItemEvent.CreateCode -> { onNavigateToScreen(Screen.Create.route) }
                                DataItemEvent.Setting -> {  }
                            }
                        }
                    ) {
                        CoilImage(
                            data = circleButton.icon,
                            contentDescription = circleButton.name,
                            modifier = Modifier.size(30.dp),
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
            }
        }
    }
}