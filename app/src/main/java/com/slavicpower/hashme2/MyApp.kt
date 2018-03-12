package com.slavicpower.hashme2

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myApiService = retrofit.create(MyApiService::class.java)

    }

    companion object {
        private const val BASE_URL = "https://api.instagram.com/v1/"
        lateinit var retrofit: Retrofit
        lateinit var myApiService: MyApiService
    }
}