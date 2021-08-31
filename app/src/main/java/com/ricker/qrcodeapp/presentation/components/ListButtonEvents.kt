package com.ricker.qrcodeapp.presentation.components

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@Composable
fun ListButtonEvents(
    modifier: Modifier,
    listItemCollections: List<DataItemEvent>,
    enable: Boolean,
) {/*
    var icon by remember {
        mutableStateOf(R.drawable.icon_2d_96px)
    }*/
    BoxWithConstraints(modifier = modifier) {
        val offset by animateDpAsState(
            if (enable) 0.dp else maxWidth,
            animationSpec = tween(300)
        )
        Column(
            modifier = Modifier.offset(x = offset)
        ) {
            listItemCollections.forEach { circleButton ->
                FloatingActionButton(
                    modifier = Modifier
                        .padding(
                            vertical = 4.dp,
                            horizontal = 10.dp
                        )
                        .size(46.dp),
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
                ) {/*
                    Icon(
                        painter = loadPicture(drawable = icon),
                        contentDescription = stringResource(id = R.string.des_icon, circleButton.name)
                    )*/
                }
            }
        }
    }
}