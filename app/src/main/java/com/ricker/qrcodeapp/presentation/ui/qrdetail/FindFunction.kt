package com.ricker.qrcodeapp.presentation.ui.qrdetail

import com.ricker.qrcodeapp.presentation.components.DetailContact
import java.util.regex.Matcher
import java.util.regex.Pattern

class FindFunction {

    fun execute(value: String): List<DetailContact> {
        val list = ArrayList<DetailContact>()
        if (isPhoneNumber(value)) {
            list.add(DetailContact.AddPhoneNumber)
            list.add(DetailContact.PhoneNumber)
        }
        if (isLinkUrl(value)) list.add(DetailContact.LinkURL)
        if (isEmailAddress(value)) list.add(DetailContact.EmailAddress)
        if (list.isEmpty()) list.add(DetailContact.SearchInGoogle)
        return list
    }

    private fun isPhoneNumber(phoneNumber: String): Boolean {
        val patterns = ("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$")
        /*val validPhoneNumbers = arrayOf(
            "2055550125", "202 555 0125", "(202) 555-0125", "+111 (202) 555-0125",
            "636 856 789", "+111 636 856 789", "636 85 67 89", "+111 636 85 67 89"
        )*/
        val pattern: Pattern = Pattern.compile(patterns)
        val matcher: Matcher = pattern.matcher(phoneNumber)
        return matcher.matches()
    }

    private fun isLinkUrl(link: String): Boolean {
        val regex = "[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b" +
                    "([-a-zA-Z0-9@:%_\\+.~#?&//=]*)"
        val pattern: Pattern = Pattern.compile(regex)
        val matcher: Matcher = pattern.matcher(link)
        return matcher.matches()
    }

    private fun isEmailAddress(email: String): Boolean {
        val regex = "^[\\\\w!#\$%&'*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#\$%&'*+/=?`{|}~^-]+)*@"+
                    "(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}"
        val pattern: Pattern = Pattern.compile(regex)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }

}