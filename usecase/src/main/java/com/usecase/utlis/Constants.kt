package com.usecase.utlis

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log

/**

 */



fun getVersionCode(context: Context?): String {
    var v = ""
    try {
        v += context?.packageManager
            ?.getPackageInfo(context.packageName, 0)?.versionCode
                Log.e("versionCode", v )
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return v
}

