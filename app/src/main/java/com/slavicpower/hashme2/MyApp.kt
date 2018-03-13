package com.slavicpower.hashme2

import android.app.Application
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        instagramApiService = retrofit.create(InstagramApiService::class.java)

    }

    companion object {
        private const val BASE_URL = "https://api.instagram.com/v1/"
        lateinit var retrofit: Retrofit
        lateinit var instagramApiService: InstagramApiService
    }
}