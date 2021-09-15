package com.ricker.qrcodeapp.presentation.ui.favorites

sealed class FavoriteState {

    data class GetFavorite(
        val isFavorite: Boolean,
    ): FavoriteState()

}