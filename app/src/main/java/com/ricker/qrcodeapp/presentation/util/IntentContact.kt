package com.ricker.qrcodeapp.presentation.util

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.ricker.qrcodeapp.presentation.components.DetailContact
import com.ricker.qrcodeapp.presentation.components.DetailContact.*

class IntentContact(
    val activity: Activity,
) {

    fun execute(detailContact: DetailContact, value: String) {
        when (detailContact) {
            AddPhoneNumber -> {}
            PhoneNumber -> openPhoneNumber(value)
            LinkURL -> openUrl(value)
            EmailAddress -> {}
            SaveToClipBoard -> {}
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        activity.startActivity(intent)
    }

    private fun openPhoneNumber(number: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$number")
        activity.startActivity(intent)
    }

}