package com.kt.core.utlities

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatDialog
import com.kt.core.R

/**
 * Created by lenovo on 1/16/2019.
 */
class Loading(context: Context) : AppCompatDialog(context) {
    init {
        setup()
    }

    private fun setup() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow()?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        getWindow()?.setGravity(Gravity.CENTER)
        setContentView(R.layout.layout_loading)
        setCancelable(false)
      //  val ivLoading = findViewById(R.id.avi)

    }
}