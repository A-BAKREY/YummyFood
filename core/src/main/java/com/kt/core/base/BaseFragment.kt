package com.spark.model.core

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.kt.core.R
import com.kt.core.databinding.FragmentBaseBinding
import com.kt.core.utlities.DialogRetry
import com.kt.core.utlities.Loading
import com.kt.core.utlities.extensions.gone
import com.kt.core.utlities.extensions.visible
import com.usecase.network.*
import retrofit2.HttpException
import java.io.IOException

import java.lang.reflect.ParameterizedType


abstract class BaseFragment<VB : ViewBinding> : Fragment() {

   val binding by lazy { initBinding() }
  private val baseBinding by lazy { FragmentBaseBinding.inflate(layoutInflater) }
  private val loadingDialog by lazy { activity?.let { Loading(it) } }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    baseBinding.flContainer.apply {
      removeAllViews()
      addView(binding.root)
    }
    dismissLoading()
    dismissInnerLoading()
    return baseBinding.root
  }

  override fun onDestroy() {
    // Dismiss dialog before fragment destroyed otherwise IllegalArgumentException will arise.
    dismissLoading()
    super.onDestroy()
  }

  /**
   * Show loading dialog
   */
  fun showLoading() {
    loadingDialog?.show()
  }

  /**
   * Dismiss loading dialog
   */
  fun dismissLoading() {
    // Make sure that fragment is alive otherwise IllegalArgumentException will arise.
    if (isDetached.not()) loadingDialog?.dismiss()
  }

  /**
   * Show inner loading view
   */
  protected fun showInnerLoading() = with(baseBinding.loadingView) { rlLoading.visible() }

  /**
   * Dismiss inner loading view
   */
  protected fun dismissInnerLoading() = with(baseBinding.loadingView) { rlLoading.gone() }

  /**
   * Show error view
   * @param drawable Image that represent status (Default is connection image)
   * @param message Status message
   * @param showRetry Show or hide retry button (Default is true)
   * @param action Retry button text (Default is Retry)
   * @param onRetry Retry button on click listener
   */
  protected fun showError(@DrawableRes drawable: Int = R.drawable.ic_cloud_off_24px,
                          message: String,
                          showRetry: Boolean = true,
                          action: String? = getString(R.string.base_retry),
                          onRetry: () -> Unit) {
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

  @Suppress("UNCHECKED_CAST")
  private fun initBinding(): VB {
    val superclass = javaClass.genericSuperclass as ParameterizedType
    val method = (superclass.actualTypeArguments[0] as Class<Any>)
        .getDeclaredMethod("inflate", LayoutInflater::class.java)
    return method.invoke(null, layoutInflater) as VB
  }

  public fun showHideDialog(show: Boolean) {
    if (show) {
      Log.e("show", "progress")
      showLoading()
    } else {
      dismissLoading()
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

          activity?.DialogRetry(getString(R.string.no_connection),msg?:"Error"){onRetry.invoke()}
        }else{
          activity?.DialogRetry(getString(R.string.error),msg?:"Error"){onRetry.invoke()}
        }
        //======================//
      }
      ERROR -> {
        showHideDialog(false)
        activity?.DialogRetry(getString(R.string.error),it.message?:"Error"){onRetry.invoke()}
      }
      LogIn -> {
        showHideDialog(false)

      }
      serverMsg -> {
        Toast.makeText(context, it.message?:"Error", Toast.LENGTH_SHORT).show()
        showHideDialog(false)
      }
    }
    return isSucess
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