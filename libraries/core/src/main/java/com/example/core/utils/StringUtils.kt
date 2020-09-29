package com.example.core.utils

import android.content.Context
import android.util.Patterns
import java.net.URLDecoder
import java.security.MessageDigest
import java.util.regex.Pattern

object StringUtils {

    val String.md5: String
        get() {
            val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
            return bytes.joinToString("") {
                "%02x".format(it)
            }
        }

    val String.sha1: String
        get() {
            val bytes = MessageDigest.getInstance("SHA-1").digest(this.toByteArray())
            return bytes.joinToString("") {
                "%02x".format(it)
            }
        }

    fun CharSequence?.isValidEmail() =
        !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    fun String.isEmailValid(): Boolean {
        val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,8}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(this)
        return matcher.matches()
    }

    fun createRoomIdFromIds(myId: String, partnerId: String): String {
        return if (myId < partnerId) {
            myId + partnerId
        } else {
            partnerId + myId
        }
    }

//    fun String.formatPhoneNumber(region: String): String? {
//        val phoneNumberKit = PhoneNumberUtil.getInstance()
//        val number = phoneNumberKit.parse(this, region)
//        if (!phoneNumberKit.isValidNumber(number))
//            return null
//
//        return phoneNumberKit.format(number, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
//    }

//    fun String.formatPhoneNumber(context: Context, region: String): String? {
////        val phoneNumberKit = PhoneNumberUtil.createInstance(context)
////        val number = phoneNumberKit.parse(this, region)
////        if (!phoneNumberKit.isValidNumber(number))
////            return null
////
////        return phoneNumberKit.format(number, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
////    }

    val String.containsLatinLetter: Boolean
        get() = matches(Regex(".*[A-Za-z].*"))

    val String.containsDigit: Boolean
        get() = matches(Regex(".*[0-9].*"))

    val String.isAlphanumeric: Boolean
        get() = matches(Regex("[A-Za-z0-9]*"))

    val String.hasLettersAndDigits: Boolean
        get() = containsLatinLetter && containsDigit

    val String.isIntegerNumber: Boolean
        get() = toIntOrNull() != null

    val String.toDecimalNumber: Boolean
        get() = toDoubleOrNull() != null

    fun String.save(
        applicationContext: Context,
        value: Map<String, Any>,
        clear: Boolean = false,
        now: Boolean = false
    ) {
        val sp = applicationContext.getSharedPreferences(this, Context.MODE_PRIVATE).edit()
        if (clear)
            sp.clear()
        value.keys.forEach { key ->
            val v = value[key]
            if (v != null) {
                when (v) {
                    is String -> sp.putString(key, v)
                    is Float -> sp.putFloat(key, v)
                    is Long -> sp.putLong(key, v)
                    is Int -> sp.putInt(key, v)
                    is Boolean -> sp.putBoolean(key, v)
                }
            }
        }
        if (now)
            sp.commit()
        else
            sp.apply()
    }

    fun String.load(applicationContext: Context): Map<String, Any> {
        val sp = applicationContext.getSharedPreferences(this, Context.MODE_PRIVATE)
        val keys = sp.all.keys
        val result = hashMapOf<String, Any>()
        keys.map { key ->
            val v = sp.all[key]
            if (v != null)
                result[key] = v
        }
        return result
    }

    val String.lastPathComponent: String
        get() {
            var path = this
            if (path.endsWith("/"))
                path = path.substring(0, path.length - 1)
            var index = path.lastIndexOf('/')
            if (index < 0) {
                if (path.endsWith("\\"))
                    path = path.substring(0, path.length - 1)
                index = path.lastIndexOf('\\')
                if (index < 0)
                    return path
            }
            return path.substring(index + 1)
        }

//    val String.awtColor: Color?
//        get() {
//            val r = substring(1, 3).toIntOrNull(16) ?: return null
//            val g = substring(3, 5).toIntOrNull(16) ?: return null
//            val b = substring(5, 7).toIntOrNull(16) ?: return null
//            return Color(r, g, b)
//        }

    val String.creditCardFormatted: String
        get() {
            val preparedString = replace(" ", "").trim()
            val result = StringBuilder()
            for (i in preparedString.indices) {
                if (i % 4 == 0 && i != 0) {
                    result.append(" ")
                }
                result.append(preparedString[i])
            }
            return result.toString()
        }

    fun getParameter(string: String, prefixName: String): String {
        val params = string.split("&")
        var value = ""
        val prefix = "$prefixName="
        for (param in params) {
            if (param.startsWith(prefix)) {
                value = param.substringAfter(prefix)
                break
            }
        }
        return value
    }

    fun handleNameDecoder(string: String, enc: String, charsets: String): String {
        return String(
            URLDecoder.decode(string, charsets).toByteArray(charset(charsets)),
            charset(enc)
        )
    }
}