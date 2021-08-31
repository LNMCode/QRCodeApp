package com.ricker.qrcodeapp.presentation.components

sealed class DataItemEvent {
    object ThreeD : DataItemEvent()

    val name: String
        get() = when (this) {
            ThreeD -> "3D"
        }
/*
    val imageResource: Int
        get() = when (this) {
            ThreeD -> R.drawable.icon_3d_96px
        }*/
}