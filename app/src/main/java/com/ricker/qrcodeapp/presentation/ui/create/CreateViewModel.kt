package com.ricker.qrcodeapp.presentation.ui.create

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CreateViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val valueString = mutableStateOf("")

    val bitmapCreated: MutableState<Bitmap?> = mutableStateOf(null)

    val enableCreateQRCode = mutableStateOf(true)

    fun createQRCode() {
        if (valueString.value.isNotBlank()){
            val qrCodeWriter = QRCodeWriter()
            val matrix = qrCodeWriter.encode(valueString.value, BarcodeFormat.QR_CODE, 350, 350)
            val encoder = BarcodeEncoder()
            bitmapCreated.value = encoder.createBitmap(matrix)
        }
    }

    fun onValueStringChange(value: String) {
        valueString.value = value
    }

    fun isEnableCreateQRCode(isEnable: Boolean){
        enableCreateQRCode.value = isEnable
    }

    fun clearValueString(){
        valueString.value = ""
    }

}