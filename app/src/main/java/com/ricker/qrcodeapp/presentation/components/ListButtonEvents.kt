package com.ricker.qrcodeapp.presentation.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.CoilImage
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@Composable
fun ListButtonEvents(
    modifier: Modifier,
    listItemCollections: List<DataItemEvent>,
    enable: Boolean,
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
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = 3.dp),
                        text = circleButton.name,
                        style = MaterialTheme.typography.button
                    )
                    FloatingActionButton(
                        modifier = Modifier.size(50.dp),
                        backgroundColor = Color.White,
                        elevation = FloatingActionButtonDefaults.elevation(2.dp),
                        onClick = {
                            /*when (circleButton) {
                                DataItemEvent.ThreeD -> {
                                    Log.d(TAG, "ListButtonEvents: 3D")
                                    onChangeModeMapView()
                                    icon = if (icon == R.drawable.icon_2d_96px) R.drawable.icon_3d_96px
                                    else R.drawable.icon_2d_96px
                                }
                                *//*DataItemEvent.GPS -> {
                                Log.d(TAG, "ListButtonEvents: GPS")
                                onGPS()
                            }*//*
                        }*/
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