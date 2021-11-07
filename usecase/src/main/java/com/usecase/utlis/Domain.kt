package com.usecase.utlis

import android.app.Application
import androidx.lifecycle.MutableLiveData

 val applicationLiveData = MutableLiveData<Application>()

 fun MutableLiveData<Application>.getApplication() = value!!

object Domain {

    fun integrateWith(application: Application) {
        applicationLiveData.value = application
    }

}