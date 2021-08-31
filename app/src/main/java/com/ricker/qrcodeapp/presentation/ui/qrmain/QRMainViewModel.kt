package com.ricker.qrcodeapp.presentation.ui.qrmain

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ricker.qrcodeapp.presentation.util.ConnectivityManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QRMainViewModel
@Inject
constructor(
    private val connectivityManager: ConnectivityManager,
    private val state: SavedStateHandle,
) : ViewModel(){
    val isEnableOptions = mutableStateOf(false)

    val isEnableQRCamera = mutableStateOf(false)

    fun setEnableQRCamera(isEnable: Boolean){
        isEnableQRCamera.value = isEnable
    }

    fun setEnableOptions(isEnable: Boolean){
        isEnableOptions.value = isEnable
    }
}