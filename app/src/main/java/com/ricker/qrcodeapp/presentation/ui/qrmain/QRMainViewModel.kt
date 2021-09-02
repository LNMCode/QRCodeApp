package com.ricker.qrcodeapp.presentation.ui.qrmain

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ricker.qrcodeapp.cache.HistoryDao
import com.ricker.qrcodeapp.cache.model.HistoryEntityMapper
import com.ricker.qrcodeapp.domain.model.History
import com.ricker.qrcodeapp.interactors.history.GetHistory
import com.ricker.qrcodeapp.interactors.history.InsertHistory
import com.ricker.qrcodeapp.presentation.ui.history.HistoryState
import com.ricker.qrcodeapp.presentation.util.ConnectivityManager
import com.ricker.qrcodeapp.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class QRMainViewModel
@Inject
constructor(
    private val getHistory: GetHistory,
    private val insertHistory: InsertHistory,
    private val connectivityManager: ConnectivityManager,
    private val state: SavedStateHandle,
) : ViewModel() {
    val isEnableOptions = mutableStateOf(false)

    val isEnableQRCamera = mutableStateOf(false)

    val historyItems: MutableState<List<History>> = mutableStateOf(ArrayList())

    val loading = mutableStateOf(false)

    init {
        onTriggerEvent(QRMainState.GetListHistoryItemEvent)
    }

    fun onTriggerEvent(event: QRMainState) {
        viewModelScope.launch {
            try {
                when (event) {
                    is QRMainState.GetListHistoryItemEvent -> {
                        getListHistory()
                    }
                    is QRMainState.InsertHistoryItem -> {
                        insertHistory(event.model)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            } finally {
                Log.d(TAG, "launchJob: finally called.")
            }
        }
    }

    private suspend fun getListHistory() {
        historyItems.value = getHistory.getAll()
    }

    private suspend fun insertHistory(model: History) {
        insertHistory.insert(model)
        onTriggerEvent(QRMainState.GetListHistoryItemEvent)
    }

    fun setEnableQRCamera(isEnable: Boolean) {
        isEnableQRCamera.value = isEnable
    }

    fun setEnableOptions(isEnable: Boolean) {
        isEnableOptions.value = isEnable
    }
}