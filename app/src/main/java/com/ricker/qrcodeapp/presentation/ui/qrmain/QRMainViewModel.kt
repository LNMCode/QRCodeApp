package com.ricker.qrcodeapp.presentation.ui.qrmain

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ricker.qrcodeapp.domain.model.History
import com.ricker.qrcodeapp.interactors.history.DeleteHistory
import com.ricker.qrcodeapp.interactors.history.GetHistory
import com.ricker.qrcodeapp.interactors.history.InsertHistory
import com.ricker.qrcodeapp.presentation.util.ConnectivityManager
import com.ricker.qrcodeapp.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QRMainViewModel
@Inject
constructor(
    private val getHistory: GetHistory,
    private val insertHistory: InsertHistory,
    private val deleteHistory: DeleteHistory,
    private val connectivityManager: ConnectivityManager,
    private val state: SavedStateHandle,
) : ViewModel() {
    val isEnableOptions = mutableStateOf(false)

    val isEnableQRCamera = mutableStateOf(false)

    val isEnableRemoveItems = mutableStateOf(false)

    val historyItems: MutableState<List<History>> = mutableStateOf(ArrayList())
    
    val numberRemoveItems = mutableStateOf(0)

    val loading = mutableStateOf(false)

    private val removeItems: MutableState<ArrayList<String>> = mutableStateOf(ArrayList())

    init {
        onTriggerEvent(QRMainState.GetListHistoryItemEvent)
    }

    fun onTriggerEvent(event: QRMainState) {
        viewModelScope.launch {
            try {
                when (event) {
                    is QRMainState.GetListHistoryItemEvent -> getListHistory()
                    is QRMainState.InsertHistoryItem -> {
                        insertHistory(event.model)
                    }
                    is QRMainState.DeleteHistory -> deleteHistoryItems()
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
        historyItems.value = getHistory.getAll().reversed()
    }

    private suspend fun insertHistory(model: History) {
        insertHistory.insert(model)
        onTriggerEvent(QRMainState.GetListHistoryItemEvent)
    }

    private suspend fun deleteHistoryItems() {
        deleteHistory.removeItemsById(removeItems.value)
        onTriggerEvent(QRMainState.GetListHistoryItemEvent)
        clearListItemsRemove()
    }

    fun setEnableQRCamera(isEnable: Boolean) {
        isEnableQRCamera.value = isEnable
    }

    fun setEnableOptions(isEnable: Boolean) {
        isEnableOptions.value = isEnable
    }

    fun setEnableRemoveItem(isEnable: Boolean){
        isEnableRemoveItems.value = isEnable
    }

    fun appendRemoveItem(id: String){
        removeItems.value.add(id)
        onChangeNumberRemoveItems(removeItems.value.size)
    }

    fun removeItem(id: String){
        removeItems.value.remove(id)
        onChangeNumberRemoveItems(removeItems.value.size)
        if (numberRemoveItems.value == 0) setEnableRemoveItem(false)
    }

    fun clearListItemsRemove(){
        removeItems.value.clear()
        setEnableRemoveItem(false)
        onChangeNumberRemoveItems(removeItems.value.size)
    }

    private fun onChangeNumberRemoveItems(number: Int){
        numberRemoveItems.value = number
    }
}