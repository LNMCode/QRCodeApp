package com.ricker.qrcodeapp.presentation.ui.qrmain

import com.ricker.qrcodeapp.domain.model.History
import com.ricker.qrcodeapp.presentation.ui.history.HistoryState

sealed class QRMainState {

    object GetListHistoryItemEvent: QRMainState()

    data class InsertHistoryItem(
        val model: History,
    ): QRMainState()
}