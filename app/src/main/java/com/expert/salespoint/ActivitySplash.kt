package com.expert.salespoint

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.expert.salespoint.auth.ActivityLogin
import com.expert.salespoint.databinding.ActivitySplashBinding
import com.kt.core.base.BaseActivity

class ActivitySplash : BaseActivity<ActivitySplashBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideToolbar()

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this,ActivityLogin::class.java))
            finish()
        }, 3000)
    }
}