package com.ricker.qrcodeapp.presentation.util

import android.R.attr.label
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.ricker.qrcodeapp.presentation.components.DetailContact
import com.ricker.qrcodeapp.presentation.components.DetailContact.*


class IntentContact(
    private val activity: Activity,
) {

    fun execute(detailContact: DetailContact, value: String) {
        when (detailContact) {
            AddPhoneNumber -> addNewPhoneNumber(value)
            PhoneNumber -> openPhoneNumber(value)
            LinkURL -> openUrl(value)
            EmailAddress -> {}
            SaveToClipBoard -> saveToClipBoard(value)
            SearchInGoogle -> openUrl("https://www.google.com/search?q=$value")
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun openPhoneNumber(number: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$number")
        startActivity(intent)
    }

    private fun addNewPhoneNumber(number: String){
        val intent = Intent(ContactsContract.Intents.Insert.ACTION)
        intent.type = ContactsContract.RawContacts.CONTENT_TYPE
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, number)
        startActivity(intent)
    }

    private fun saveToClipBoard(text: String){
        val clipboard: ClipboardManager? =
            activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = ClipData.newPlainText("QRCopyText", text)
        clipboard?.setPrimaryClip(clip)
        Toast.makeText(activity, "Saved to clipboard!", Toast.LENGTH_SHORT).show()
    }

    private fun startActivity(intent: Intent){
        activity.startActivity(intent)
    }

}