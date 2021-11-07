@file:Suppress("DEPRECATION")

package com.usecase.network


import android.annotation.SuppressLint
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.usecase.utlis.applicationLiveData
import com.usecase.utlis.enumSh
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RestClient {

      private const val BASE_URL = "http://142.93.116.98/gotoque/api/"
    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient)
            .build().create(ApiService::class.java)
    }

    private val httpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(headerInterceptor())
            .addInterceptor(loggingInterceptor())
            .build()
    }

    @SuppressLint("HardwareIds")
    private fun headerInterceptor(): Interceptor {
        return Interceptor {
            val original = it.request()
            val auth = applicationLiveData.value?.prefGetString(enumSh.shToken.name, "")
            val playerId: String? =
                applicationLiveData.value?.prefGetString(enumSh.playerId.name, "")
            val deviceId: String? = applicationLiveData.value?.prefGetString(enumSh.refId.name, "")
            if (auth.equals("")) {
                val request = original.newBuilder()
                    .header("Platform", "android")
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")

                    .build()

                it.proceed(request)
            } else {

                val tokenBearer = "Bearer".plus(" ").plus(auth)
                val request = original.newBuilder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")


                    .build()

                it.proceed(request)
            }
        }
    }

    private fun loggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (true) BODY else BODY
        }
    }
}