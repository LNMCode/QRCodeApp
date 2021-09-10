package com.ricker.qrcodeapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.CoilImage
import com.ricker.qrcodeapp.R
import com.ricker.qrcodeapp.presentation.navigation.Screen

@Composable
fun DetailContactItem(
    detailContact: DetailContact,
    value: String,
    intentContact: (DetailContact, String) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable {
                intentContact(detailContact, value)
            },
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            modifier = Modifier
                .padding(15.dp),
        ) {
            CoilImage(
                data = detailContact.icon,
                contentDescription = "Icon detail",
                modifier = Modifier.size(40.dp),
                contentScale = ContentScale.Crop,
            )
            Text(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .align(Alignment.CenterVertically),
                text = detailContact.title,
                style = MaterialTheme.typography.subtitle1,
            )
        }
    }
}

sealed class DetailContact() {
    object AddPhoneNumber : DetailContact()
    object PhoneNumber : DetailContact()
    object LinkURL : DetailContact()
    object EmailAddress : DetailContact()
    object SaveToClipBoard : DetailContact()

    val title: String
        get() = when (this) {
            AddPhoneNumber -> "Add phone number"
            PhoneNumber -> "Phone Number"
            LinkURL -> "Link url"
            EmailAddress -> "Email address"
            SaveToClipBoard -> "Save to clipboard"
        }

    val icon: Int
        get() = when (this) {
            AddPhoneNumber -> R.drawable.ic_add_phone_number
            PhoneNumber -> R.drawable.ic_phone
            LinkURL -> R.drawable.ic_website
            EmailAddress -> R.drawable.ic_send_email
            SaveToClipBoard -> R.drawable.ic_save
        }
}