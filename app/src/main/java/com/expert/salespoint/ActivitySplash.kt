package com.expert.salespoint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.expert.salespoint.databinding.ActivitySplashBinding
import com.expert.salespoint.home.intro.IntroActivity
import com.kt.core.base.BaseActivity

class ActivitySplash : BaseActivity<ActivitySplashBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideToolbar()

        animate()

        Handler().postDelayed({
            val intent = Intent(this,IntroActivity::class.java)
            startActivity(intent)
            finish()
        },4050)
    }

    private fun animate(){
        binding.logo2.animate().translationY(-2200F).setDuration(2000).startDelay = 2900
        binding.appName.animate().translationY(-2300F).setDuration(2000).startDelay = 3000
        binding.lottie.animate().translationY(2000F).setDuration(1000).startDelay = 3000
    }

}