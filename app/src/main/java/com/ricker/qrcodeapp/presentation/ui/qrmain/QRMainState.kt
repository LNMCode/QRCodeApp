package com.ricker.qrcodeapp.presentation.ui.qrmain

import com.ricker.qrcodeapp.domain.model.History

sealed class QRMainState {

    object GetListHistoryItemEvent: QRMainState()

    data class InsertHistoryItem(
        val model: History,
    ): QRMainState()

}