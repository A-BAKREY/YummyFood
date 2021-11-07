package com.expert.salespoint.home

import android.os.Bundle
import com.expert.salespoint.databinding.ActivityMainBinding
import com.kt.core.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideToolbar()

    }
}