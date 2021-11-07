package com.kt.core.base

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.kt.core.R
import com.kt.core.databinding.ActivityBaseBinding
import com.kt.core.utlities.DialogRetry
import com.kt.core.utlities.Loading
import com.kt.core.utlities.LocaleHelper
import com.kt.core.utlities.extensions.gone
import com.kt.core.utlities.extensions.hideKeyboard
import com.kt.core.utlities.extensions.visible
import com.usecase.network.*
import com.usecase.utlis.enumSh
import es.dmoral.toasty.Toasty
import retrofit2.HttpException
import java.io.IOException
import java.lang.reflect.ParameterizedType


abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    public val binding by lazy { initBinding() }
    private val baseBinding by lazy { ActivityBaseBinding.inflate(layoutInflater) }
    private val loadingDialog by lazy { Loading(this) }
    private var progressDialog: Dialog? = null
    
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        setContent()
    }

    @Suppress("UNCHECKED_CAST")
    private fun initBinding(): VB {
        val superclass = javaClass.genericSuperclass as ParameterizedType
        val method = (superclass.actualTypeArguments[0] as Class<Any>)
            .getDeclaredMethod("inflate", LayoutInflater::class.java)
        return method.invoke(null, layoutInflater) as VB
    }

    private fun setContent() {
        baseBinding.flContainer.addView(binding.root)
        setContentView(baseBinding.root)
    }

    override fun onPause() {
        hideKeyboard(currentFocus)
        super.onPause()
    }

    /**
     * Show loading dialog
     */
    fun showLoading() = loadingDialog.show()

    /**
     * Dismiss loading dialog
     */
    fun dismissLoading() {
        // Make sure that activity is alive otherwise IllegalArgumentException will arise.
        if (isDestroyed.not()) loadingDialog.dismiss()
    }


    protected fun showError(
        @DrawableRes drawable: Int = R.drawable.ic_no_signal,
        message: String,
        showRetry: Boolean = true,
        action: String? = getString(R.string.base_retry),
        onRetry: () -> Unit,
    ) {
        with(baseBinding.errorView) {
            ivError.setImageResource(drawable)
            tvError.text = message
            if (showRetry) {
                btnRetry.visible()
                btnRetry.text = action
                btnRetry.setOnClickListener {
                    onRetry.invoke()
                    llError.gone()
                }
            }
            llError.visible()
        }
    }

    private fun showProgressDialog() {

        if (progressDialog == null) {

            progressDialog = Loading(this)

        }
        progressDialog?.let {
            if (!it.isShowing) {
                it.show()
            }
        }

    }

    private fun hideProgressDialog() {
        progressDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }


    }

    public fun showHideDialog(show: Boolean) {
        if (show) {
            Log.e("show", "progress")
            showProgressDialog()
        } else {
            Log.e("hide", "progress")
            hideProgressDialog()
        }

    }

    fun <T> callApi(it: Resource<T>, onRetry: () -> Unit): Boolean {
        var isSucess: Boolean = false
        when (it.status) {
            LOADING -> showHideDialog(true)
            SUCCESS -> {
                showHideDialog(false)
                isSucess = true
            }
            ERRORNet -> {
                showHideDialog(false)
             //==========//
             val e=it.exception
             val msg= e?.let { it1 -> getErrorMsg(it1) }
                if(e is IOException){

                  DialogRetry(getString(R.string.no_connection),msg?:"Error"){onRetry.invoke()}
                }else{
                    DialogRetry(getString(R.string.error),msg?:"Error"){onRetry.invoke()}
                }
                //======================//
            }
            ERROR -> {
                showHideDialog(false)
                DialogRetry(getString(R.string.error),it.message?:"Error"){onRetry.invoke()}
            }
            LogIn -> {
                showHideDialog(false)
                val c = Class.forName("com.ksi.go_toque_customer.ui.loginregister.ActivityLogInRegister")
                intent = Intent(this, c)
                startActivity(intent)
                finish()
                prefadd(enumSh.shToken.name, "")
                prefadd(enumSh.shUser.name, "")
                prefadd(enumSh.userId.name, "")

            }
            serverMsg -> {
                Toasty.error(this, it.message?:"Error").show()
                showHideDialog(false)
            }
        }
        return isSucess
    }
    fun enableHomeAsUp() {
        setSupportActionBar(baseBinding.appToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//         app_toolbar?.title="ahmed"
    }

    protected fun setTitle(title: String) {
        setSupportActionBar(baseBinding.appToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        baseBinding.appToolbar.title = title
    }

    fun setTitle_Color(
        colorTitle: Int,
        colorArrow: Int,
        colorBackground: Int,
        title: String,
        gravityStart: Boolean,
    ) {
        setSupportActionBar(baseBinding.appToolbar)
        baseBinding.toolbarTitle.setTextAppearance(this, R.style.tool_bar)
        if (!gravityStart) {
            baseBinding.appToolbar.setTitle("")
            baseBinding.toolbarTitle.text = title
        } else {
            //app_toolbar.setTitle(title)
            // app_toolbar.setTitleTextColor(colorTitle)
            baseBinding.appToolbar.setTitle("")
            baseBinding.toolbarTitle.text = title
            val params = androidx.appcompat.widget.Toolbar.LayoutParams(
                androidx.appcompat.widget.Toolbar.LayoutParams.WRAP_CONTENT,
                androidx.appcompat.widget.Toolbar.LayoutParams.WRAP_CONTENT
            ).apply {

                gravity = Gravity.START
            }
            baseBinding.toolbarTitle.layoutParams = params
        }
        baseBinding.toolbarTitle.setTextColor(colorTitle)
        // toolbar_title.setTypeface(face)
        var upArrow: Drawable = getResources().getDrawable(R.drawable.abc_ic_ab_back_material)
        upArrow.setColorFilter(colorArrow, PorterDuff.Mode.SRC_ATOP)
        getSupportActionBar()?.setHomeAsUpIndicator(upArrow);
        baseBinding.appToolbar.setBackgroundColor(colorBackground)


    }

    protected fun hideToolbar() {
        baseBinding.appToolbar.visibility = View.GONE
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    fun getErrorMsg(e: Exception) = when (e) {

        is HttpException -> {
            val body = e.response()?.errorBody()
            body?.let { Log.e("error", it.string()) }
            ""
        }
        is IOException ->
            // Timeout
           getString(R.string.network_check_connection)

        is RuntimeException -> // Unexpected Json response from server
            //  getStringByLocal(R.string.network_unexpected_response)
           getString(R.string.network_unexpected_response)

        else -> // Other error
            // getStringByLocal(R.string.network_server_unreachable)
           getString(R.string.network_server_unreachable)

    }

}