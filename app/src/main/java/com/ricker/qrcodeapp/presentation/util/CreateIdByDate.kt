package com.ricker.qrcodeapp.presentation.util

import android.annotation.SuppressLint
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class CreateIdByDate {
    companion object{
        @SuppressLint("SimpleDateFormat")
        fun create(): String {
            val dNow = Date()
            val ft = SimpleDateFormat("yyMMddhhmmssMs")
            val datetime: String = ft.format(dNow)
            Log.d(TAG, "create: $datetime")
            return datetime
        }
    }
}