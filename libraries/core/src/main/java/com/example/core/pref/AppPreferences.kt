package com.example.core.pref

import android.content.Context
import android.content.SharedPreferences
import com.example.core.utils.Constants
import javax.inject.Inject
import javax.inject.Singleton

private const val PREF_PARAM_USER_INFO = "PREF_PARAM_USER_INFO"
private const val PREF_PARAM_IS_FIRST_TIME_MOSAIC = "PREF_PARAM_IS_FIRST_TIME_MOSAIC"
private const val PREF_PARAM_VIDEO_SETTING = "PREF_PARAM_VIDEO_SETTING"
private const val PREF_PARAM_SETTING_ORGANIC = "PREF_PARAM_SETTING_ORGANIC"

@Singleton
class AppPreferences @Inject constructor(context: Context) :
    RxPreferences {

    private val mPrefs: SharedPreferences = context.getSharedPreferences(
        Constants.PREF_FILE_NAME,
        Context.MODE_PRIVATE
    )

    override fun put(key: String, value: String) {
        val editor: SharedPreferences.Editor = mPrefs.edit()
        editor.putString(key, value)
        editor.apply()
    }

    override fun get(key: String): String? {
        return mPrefs.getString(key, "")
    }

    override fun clear() {
        val editor: SharedPreferences.Editor = mPrefs.edit()
        editor.clear()
        editor.apply()
    }

    override fun remove(key: String) {
        val editor: SharedPreferences.Editor = mPrefs.edit()
        editor.remove(key)
        editor.apply()
    }
}