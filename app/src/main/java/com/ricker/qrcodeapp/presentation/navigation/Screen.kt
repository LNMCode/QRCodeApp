package com.ricker.qrcodeapp.presentation.navigation

sealed class Screen(
    val route: String
) {
    object History: Screen("history")

    object Create: Screen("create")

    object QRMain: Screen("qrMain")

    object Favorites: Screen("favorites")

    object Settings: Screen("settings")
}