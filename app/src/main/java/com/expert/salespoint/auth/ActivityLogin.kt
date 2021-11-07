package com.expert.salespoint.auth

import android.os.Bundle
import androidx.activity.viewModels
import com.expert.salespoint.auth.viewmodel.LoginViewModel
import com.expert.salespoint.databinding.ActivityLoginBinding
import com.kt.core.base.BaseActivity

class ActivityLogin : BaseActivity<ActivityLoginBinding>() {
    val viewModel by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideToolbar()

    }
}