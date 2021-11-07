package com.usecase.network


import android.util.Log
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.usecase.network.Resource.Companion.failure
import com.usecase.network.Resource.Companion.failureConnect
import com.usecase.network.Resource.Companion.loading
import com.usecase.network.Resource.Companion.serverMessage
import com.usecase.network.Resource.Companion.success
import com.usecase.network.Resource.Companion.unauthorized
import kotlinx.coroutines.Dispatchers
import retrofit2.Response

fun <T> loadData(request: suspend () -> Response<BaseModel<T>>) = liveData(Dispatchers.IO) {
    emit(loading())
    try {
        val response = request.invoke()
        val rsm = if (response.isSuccessful) response.body() else {
            val type = object : TypeToken<BaseModel<T>>() {}.type
            Gson().fromJson(response.errorBody()!!.charStream(), type) as BaseModel<T>
        }

        if (response.isSuccessful && rsm != null) {
            if (!rsm.status.status) {
                if (rsm.status.validation_message!=null&&rsm.status.validation_message.isNotEmpty()){
                    rsm.status.messages=rsm.status.validation_message
                    emit(serverMessage(rsm.status.validation_message))
                }else{
                    emit(serverMessage(rsm.status.messages))
                }
            }else {
                emit(success(rsm.data, rsm.status.messages, rsm.pagination))
            }

        }
        else if (response.code() == 401 && rsm != null){
           emit( unauthorized(""))
            //   response.errorBody()?.string()?.let { Log.e("respoo", it) }
            // emit(failure( "Error"))
        }
        else emit(failure(rsm?.status?.messages ?: "network error"))
    } catch (e: Exception) {
        Log.e("okhttp", "$e")
        emit(failureConnect(e))

}
}

fun <T> loadDataGeneric(request: suspend () -> Response<T>) = liveData(Dispatchers.IO) {
    emit(loading())
    try {
        val response = request.invoke()
        val rsm = if (response.isSuccessful) response.body() else {
            val type = object : TypeToken<T>() {}.type
            Gson().fromJson(response.errorBody()!!.charStream(), type) as T
        }

        if (response.isSuccessful && rsm != null)
            emit(success(rsm))
        else if (response.code() == 401 && rsm != null){
            unauthorized("")
         //   response.errorBody()?.string()?.let { Log.e("respoo", it) }
          // emit(failure( "Error"))
        }
        // response.errorBody()?.let { Log.e("respnddd", it.charStream().toString()) }
        // emit(logout())

        else  emit(failure( "Error"))
    } catch (e: Exception) {
        emit(failureConnect(e))


    }
}


