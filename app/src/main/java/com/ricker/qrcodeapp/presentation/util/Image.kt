package com.ricker.qrcodeapp.presentation.util

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.util.*


class Image {
    companion object{
        @SuppressLint("SimpleDateFormat")
        fun saveImageFromBitmap(finalBitmap: Bitmap): File {
            var outputStream: FileOutputStream? = null
            val file = Environment.getExternalStorageDirectory()
            val dir = File(file.absolutePath + "/MyPicsQRcode")
            dir.mkdirs()

            val filename = String.format("%d.png", System.currentTimeMillis())
            val outFile = File(dir, filename)
            try {
                outputStream = FileOutputStream(outFile)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            try {
                outputStream!!.flush()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            try {
                outputStream!!.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return outFile
        }
    }
}