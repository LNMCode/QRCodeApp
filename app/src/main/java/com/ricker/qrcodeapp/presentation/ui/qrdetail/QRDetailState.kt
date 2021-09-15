package com.ricker.qrcodeapp.presentation.ui.qrdetail

sealed class QRDetailState {

    data class GetHistoryById(
        val id: String,
    ): QRDetailState()

    data class UpdateFavorite(
        val isFavorite: Boolean,
        val idHistoryItem: String,
    ): QRDetailState()

}