package com.ricker.qrcodeapp.presentation

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.ricker.qrcodeapp.R


class CustomActivity : AppCompatActivity(), DecoratedBarcodeView.TorchListener {
    private lateinit var capture: CaptureManager
    private lateinit var bcScanner: DecoratedBarcodeView
    private lateinit var flashLight: LinearLayout
    private lateinit var imageScanner: LinearLayout
    private lateinit var textFlashLight: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom)
        bcScanner = findViewById(R.id.bcScanner)
        bcScanner.setTorchListener(this)
        flashLight = findViewById(R.id.flashlight)
        imageScanner = findViewById(R.id.image_scanner)
        textFlashLight = findViewById(R.id.text_flash_light)

        flashLight.setOnClickListener { switchFlashlight() }
        // if the device does not have flashlight in its camera,
        // then remove the flash light button...
        if (!hasFlash()) {
            flashLight.visibility = View.GONE
        }

        capture = CaptureManager(this, bcScanner)
        capture.apply {
            initializeFromIntent(intent, savedInstanceState)
            decode()
        }
    }

    override fun onResume() {
        super.onResume()
        capture.onResume()
    }

    override fun onPause() {
        super.onPause()
        capture.onPause()
    }

    override fun onDestroy() {
        capture.onDestroy()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture.onSaveInstanceState(outState)
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return bcScanner.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)
    }

    private fun hasFlash(): Boolean {
        return applicationContext.packageManager
            .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }

    private fun switchFlashlight() {
        if (getString(R.string.turn_on_flashlight) == textFlashLight.text) {
            bcScanner.setTorchOn()
        } else {
            bcScanner.setTorchOff()
        }
    }

    override fun onTorchOn() {
        textFlashLight.setText(R.string.turn_off_flashlight)
    }

    override fun onTorchOff() {
        textFlashLight.setText(R.string.turn_on_flashlight)
    }
}