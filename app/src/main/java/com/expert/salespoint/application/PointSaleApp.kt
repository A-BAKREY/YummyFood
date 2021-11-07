package com.expert.salespoint.application

import android.app.Application
import com.usecase.utlis.Domain

/**
 *
 *
 * 09/02/2021
 */

class GoToqueApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Domain.integrateWith(this)
    }

}