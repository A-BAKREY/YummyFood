package com.usecase.network

import android.content.Context
import com.google.gson.Gson

/**
 * Created by lenovo on 12/25/2018.
 */
fun Context.prefadd(key: String, defaultValue: Int) {
    val preferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE)
    val editor = preferences.edit()
    editor.putInt(key, defaultValue)
    editor.apply()

}
fun Context.prefadd(key: String, defaultValue: String) {
    val preferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE)
    val editor = preferences.edit()
    editor.putString(key, defaultValue)
    editor.apply()

}
fun Context.prefadd(key: String, defaultValue: Boolean) {
    val preferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE)
    val editor = preferences.edit()
    editor.putBoolean(key, defaultValue)
    editor.apply()

}


fun Context.prefget(key: String, defaultValue: Int): Int {
    val preferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE)
    return preferences.getInt(key, defaultValue)
}
fun Context.prefGetString(key: String, defaultValue: String): String {
    val preferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE)
    return preferences.getString(key, defaultValue)?:""
}
fun Context.prefGetString(key: String, defaultValue: Boolean): Boolean {
    val preferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE)
    return preferences.getBoolean(key, defaultValue)
}
fun Context.prefadd(key: String, value: Any) {
    val preferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE)
    val editor = preferences.edit()
    editor.putString(key, Gson().toJson(value))
    editor.apply()

}

fun <T> Context.prefget(key: String, classType: Class<T>): T? {
    val preferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE)
    return if (preferences.contains(key)) {
        Gson().fromJson(preferences.getString(key, ""), classType)
        //Gson().fromJson("", classType)
    } else {
        null
    }

}
    fun Context.get(key: String, defaultValue: String): String? {
        val preferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE)
        return preferences.getString(key, defaultValue)
    }

    fun  Context.prefclearAll() {
        val preferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }
fun  Context.prefRemove(key:String) {
    val preferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE)
    val editor = preferences.edit()
    editor.remove(key)
    editor.apply()
}
