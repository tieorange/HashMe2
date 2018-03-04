package com.slavicpower.hashme2

import android.app.Application
import com.slavicpower.hashme2.data.ResponseInstaByTag
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


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

interface MyApiService {

    @GET("tags/{tag}/media/recent?access_token=665347246.198e688.b9bd9e67045945e49726fd812fcde39b")
    fun getPicsByTag(@Path("tag") tag: String): Call<ResponseInstaByTag>
}