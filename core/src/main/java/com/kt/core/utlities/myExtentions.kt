package com.kt.core.utlities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Adapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.core.app.ShareCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.textfield.TextInputLayout
import com.kt.core.R
import com.usecase.utlis.applicationLiveData
import com.usecase.network.prefclearAll
import java.util.*


/**
 * Created by lenovo on 12/26/2018.
 */
fun getVersionCode(): String {

    var v = ""
    try {

        v += applicationLiveData.value?.packageName?.let {
            applicationLiveData.value?.packageManager
                ?.getPackageInfo(it, 0)?.versionName
        }

    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }

    // Log.e("log",v);
    return v

}

@SuppressLint("HardwareIds")
fun Context.getUUidDeviceCode(): String {

    val tManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
    val uuid = tManager!!.deviceId

    return uuid

}

fun EditText.isPhone(msg: String): Boolean {
    val p = "^1([34578])\\d{9}\$".toRegex()
    if (!this.text.toString().matches(p)) {
        this.setError(msg)
    }
    return this.text.toString().matches(p)
}

fun EditText.isEmail(msg: String): Boolean {
//    val p = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)\$".toRegex()
    /*
     val p = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()

     if (!this.text.toString().matches(p)) {
         this.setError(msg)
     }
     return this.text.toString().matches(p)

     */
    val txt=this.text.toString()

    if (txt.isNullOrEmpty() ||! Patterns.EMAIL_ADDRESS.matcher(txt).matches()) {
        this.setError(msg)
    }

    return  !txt.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(txt).matches()
}

fun EditText.isEmpty(msg: String): Boolean {
    if (this.text.toString().isEmpty()) {
        this.setError(msg)
    }
    return this.text.toString().isEmpty()
}
fun EditText.isPasswordMatch(passwrod: String, msg: String): Boolean {
    if (this.text.toString().isEmpty()||!this.text.toString().equals(passwrod)) {
        this.setError(msg)
    }
    return this.text.toString().equals(passwrod)
}
fun EditText.minChar(msg: String, number: Int): Boolean {
    if (this.text.toString().length>=number) {
        return true
       // this.setError(msg)
    }
    else{
        this.setError(msg)
       return false
    }

}

fun TextInputLayout.isPhone(input: EditText, msg: String): Boolean {

    val p = "^1([34578])\\d{9}\$".toRegex()
    if (!input.text.toString().matches(p)) {
        this.setError(msg)
    }
    //return input.text.toString().matches(p)
    return !input.text.toString().equals("")
}

fun TextInputLayout.isEmail(text: EditText, msg: String): Boolean {
    val p = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)\$".toRegex()
    if (!text.toString().matches(p)) {
        this.setError(msg)
    }
    return text.toString().matches(p)
}

fun TextInputLayout.isEmpty(text: EditText, msg: String): Boolean {
    val input: String = text.text.toString()
    if (input.equals("")) {
        this.setError(msg)
    } else {
        this.setError(null)
    }
    return input.equals("")
}
fun TextInputLayout.isPhoneValid(text: EditText, msg: String): Boolean {
    val input: String = text.text.toString()
    if (input.length!=10||!input.startsWith("5")) {
        this.setError(msg)
    } else {
        this.setError(null)
    }
    return input.equals("")
}
fun TextInputLayout.isPhoneValid(isvalid: Boolean, text: EditText, msg: String): Boolean {
    val input: String = text.text.toString()
    if (!isvalid) {
        this.setError(msg)
    } else {
        this.setError(null)
    }
    return input.equals("")
}
fun TextInputLayout.IsMinCharNotValid(text: EditText, msg: String, number: Int = 6): Boolean {
    if (text.text.toString().length>=number) {
        this.setError(null)
        return false
    }
    else{
        this.setError(msg)
        return true
    }

}

fun Context?.toast(@StringRes textId: Int, duration: Int = Toast.LENGTH_LONG) =
    this?.let { Toast.makeText(it, textId, duration).show() }


fun NestedScrollView.initLoadMore(loadMore: () -> Unit) {
    viewTreeObserver
        .addOnScrollChangedListener(object : ViewTreeObserver.OnScrollChangedListener {
            override fun onScrollChanged() {
                val view: View = getChildAt(getChildCount() - 1) as View
                if (view.getBottom() > 0) {
                    val diff: Int = view.getBottom() - (getHeight() + getScrollY())

                    if (diff == 0) {
                        loadMore()
                        Log.e("loadmorensted", "yes")
                    }
                }

            }
        })
}

fun <T> openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(applicationLiveData.value, it)
    intent.putExtras(Bundle().apply(extras))
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    applicationLiveData.value?.startActivity(intent)

}
fun <T> openActivityAndClear(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(applicationLiveData.value, it)
    intent.putExtras(Bundle().apply(extras))
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    applicationLiveData.value?.startActivity(intent)

}

fun <T> openActivity(context: Context, it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(context, it)
    intent.putExtras(Bundle().apply(extras))
    context.startActivity(intent)

}


/*
openActivity(MyActivity::class.java) {
    putString("string.key", "string.value")
    putInt("string.key", 43)
    ...
}*/
fun EditText.onChange(cb: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            cb(s.toString().trim())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}


fun AppCompatActivity.openFragment(fragment: Fragment, container: Int) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(container, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
}
fun AppCompatActivity.currentFragment(container: Int): Fragment? {
    val fragmentManager: FragmentManager = supportFragmentManager
    val currentF: Fragment? = fragmentManager.findFragmentById(container)
    return  currentF
}

fun TextView.underline() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun Context.setLangaugeAr() {
    val locale = Locale("ar")
    Locale.setDefault(locale)
    val config = Configuration()
    config.locale = locale
    getResources().updateConfiguration(
        config,
        getResources().getDisplayMetrics()
    )
    //LocaleHelper.setLangAr()
}

fun Context.setLangaugeEn() {
    val locale = Locale("en")
    Locale.setDefault(locale)
    val config = Configuration()
    config.locale = locale
    getResources().updateConfiguration(
        config,
        getResources().getDisplayMetrics()
    )
    //LocaleHelper.setLangEn()
}

/*fun Context.setdefaullanguage() {
    //  val lang = prefGetString(enumShared.shLanguage.name, "ar")
    // LocaleHelper.onAttach(applicationContext)
    // LocaleHelper.setLocale(lang)
    if (LocaleHelper.isLangAr()) {
        setLangaugeAr()
    } else {
        setLangaugeEn()
    }
}*/

/*fun Context.fixupLocale() {
    var newLocale = Locale("en")
    if (isLanguageArabic()) {
        newLocale = Locale("ar")

    }
    val res: Resources = resources
    val config: Configuration = res.getConfiguration()
    val curLocale = getLocale(config)
    if (!curLocale.equals(newLocale)) {
        Locale.setDefault(newLocale)
        val conf = Configuration(config)
        conf.setLocale(newLocale)
        res.updateConfiguration(conf, res.getDisplayMetrics())
    }
}*/
/*
fun Context.initLang() {
    try {
        val lang = prefGetString(enumShared.shLanguage.name, "ar")

        val config: Configuration = getResources().getConfiguration()
        val locale = Locale(lang)
        Locale.setDefault(locale)
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale)
        } else {
            config.locale = locale
        }
       getResources()
            .updateConfiguration(config, getResources().getDisplayMetrics())
    } catch (e: Exception) {
    }
}
*/

fun getLocale(config: Configuration): Locale {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        config.locales[0]
    } else {
        config.locale
    }
}
fun Group.addOnClickListener(listener: (view: View) -> Unit) {
    referencedIds.forEach { id ->
        rootView.findViewById<View>(id).setOnClickListener(listener)
    }
}
fun Any?.toStringOrEmpty() = this?.toString() ?: ""
fun AppCompatActivity.openLinkInBrowser(urls: String) {
    val uris = Uri.parse(urls)
    val intents = Intent(Intent.ACTION_VIEW, uris)
    // val b = Bundle()
    // b.putBoolean("new_window", true)
    //  intents.putExtras(b)
    startActivity(intents)
}
fun AppCompatActivity.shareApp(){
    ShareCompat.IntentBuilder.from(this)
        .setType("text/plain")
        .setChooserTitle("Chooser title")
        .setText("http://play.google.com/store/apps/details?id=" + getPackageName())
        .startChooser();
}

fun AppCompatActivity.openActivityByRouting(route: String){
    val activityToStart = intent.getStringExtra(route)
    val c = Class.forName(activityToStart)
    intent = Intent(this, c)
   startActivity(intent)
    finish()

}
fun Context.forceLogIn(){
    Toast.makeText(this, getString(R.string.need_log_in), Toast.LENGTH_LONG).show()
    prefclearAll()
    val startMain = Intent(Intent.ACTION_MAIN)
    startMain.addCategory(Intent.CATEGORY_LAUNCHER)
    startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(startMain)
}
fun EditText.TxT():String{
    return this.text.toString()
}

fun Context.dialContactPhone(phoneNumber: String) {
    startActivity(Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)))
}





