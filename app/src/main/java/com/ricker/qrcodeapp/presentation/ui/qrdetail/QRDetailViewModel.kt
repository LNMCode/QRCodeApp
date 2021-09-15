package com.ricker.qrcodeapp.presentation.ui.qrdetail

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ricker.qrcodeapp.domain.model.History
import com.ricker.qrcodeapp.interactors.history.GetHistory
import com.ricker.qrcodeapp.interactors.history.UpdateHistory
import com.ricker.qrcodeapp.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QRDetailViewModel
@Inject
constructor(
    private val getHistory: GetHistory,
    private val updateHistory: UpdateHistory,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {
    val historyItem = mutableStateOf<History?>(null)

    val isFavorite = mutableStateOf(false)

    fun onTriggerEvent(event: QRDetailState){
        viewModelScope.launch {
            try {
                when(event){
                    is QRDetailState.GetHistoryById -> {
                        getHistoryById(event.id)
                    }
                    is QRDetailState.UpdateFavorite -> {
                        updateFavoriteHistory(
                            isFavorite = event.isFavorite,
                            idHistoryItem = event.idHistoryItem,
                        )
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

    private suspend fun updateFavoriteHistory(isFavorite: Boolean, idHistoryItem: String) {
        updateHistory.updateFavorite(isFavorite, idHistoryItem)
        setIsFavorite(isFavorite)
    }

    private suspend fun getHistoryById(id: String) {
        historyItem.value = getHistory.getById(id)
        setIsFavorite(historyItem.value!!.isFavorite)
    }

    private fun setIsFavorite(isFavorite: Boolean){
        this.isFavorite.value = isFavorite
    }
}