package com.ricker.qrcodeapp.presentation.ui.qrdetail

sealed class QRDetailState {

    data class GetHistoryById(
        val id: String,
    ): QRDetailState()

}