package com.ricker.qrcodeapp.presentation.components

import com.ricker.qrcodeapp.R
import com.ricker.qrcodeapp.presentation.navigation.Screen

sealed class DataItemEvent {
    object ScanCode: DataItemEvent() //QRMain
    object CreateCode: DataItemEvent()
    object QRDetail: DataItemEvent()
    object Favorites: DataItemEvent()
    object Setting: DataItemEvent()

    val name: String
        get() = when (this) {
            ScanCode -> "Scan Code"
            CreateCode -> "Create Code"
            QRDetail -> "History"
            Favorites -> "Favorites"
            Setting -> "Setting"
        }

    val icon: Int
        get() = when (this) {
            ScanCode -> R.drawable.ic_qr_code
            CreateCode -> R.drawable.ic_create_qr_code
            QRDetail -> R.drawable.ic_history
            Favorites -> R.drawable.ic_favorites
            Setting -> R.drawable.ic_setting
        }

    val event: String
        get() = when(this){
            ScanCode -> ""
            CreateCode -> Screen.Create.route
            QRDetail -> Screen.QRDetail.route
            Favorites -> Screen.Favorites.route
            Setting -> Screen.Settings.route
        }
}

val listItemEvent = listOf<DataItemEvent>(
    DataItemEvent.ScanCode,
    DataItemEvent.CreateCode,
    DataItemEvent.Favorites,
    DataItemEvent.Setting,
)