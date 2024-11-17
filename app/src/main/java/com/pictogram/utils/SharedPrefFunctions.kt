package com.pictogram.utils

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import androidx.core.content.edit
import com.google.gson.Gson
import timber.log.Timber
import java.lang.reflect.Type

class SharedPrefFunctions(_mcontext: Context) {

    private val mSharedPreferences: SharedPreferences =
        _mcontext.getSharedPreferences("MyPref", Context.MODE_PRIVATE)

    fun setPref(title: String?, value: Boolean) {
        mSharedPreferences.edit().putBoolean(title, value).apply()
    }

    fun setPref(title: String?, value: String?) {
        mSharedPreferences.edit().putString(title, value).apply()
    }

    fun setPref(title: String?, value: Int) {
        mSharedPreferences.edit().putInt(title, value).apply()
    }

    fun <T> setPref(title: String?, value: T) {
        val jsonValue = Gson().toJson(value)
        mSharedPreferences.edit().putString(title, jsonValue).apply()
    }

    fun saveUri(title: String?, value: Uri) {
        mSharedPreferences.edit().putString(title, value.toString()).apply()
    }

    fun getPref(title: String?, def: Boolean): Boolean {
        return mSharedPreferences.getBoolean(title, def)
    }

    fun getPref(title: String?, def: String?): String? {
        return mSharedPreferences.getString(title, def)
    }


    fun getPref(title: String?, def: Int): Int {
        return mSharedPreferences.getInt(title, def)
    }

    fun getUri(title: String?, def: String?): Uri {
        val uriString = mSharedPreferences.getString(title, def)
        return Uri.parse(uriString)
    }

    fun <T> getObject(title: String?, type: Type): T? {
        val value = mSharedPreferences.getString(title, null)
        return Gson().fromJson(value, type)
    }


    private fun saveToSharedPref(key: String, objectValue: Any?) {
        val serializedObject = Gson().toJson(objectValue)
        saveToSharedPref(key, serializedObject)
    }

    private fun saveToSharedPref(key: String, value: String?) {
        Timber.e("saving to prefs key => $key, value => $value")
        mSharedPreferences.edit(true) {
            putString(key, value)
        }
    }
}
