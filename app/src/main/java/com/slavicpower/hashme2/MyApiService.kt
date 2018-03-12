package com.slavicpower.hashme2

import com.slavicpower.hashme2.data.ResponseInstaByTag
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MyApiService {

    @GET("tags/{tag}/media/recent?access_token=665347246.198e688.b9bd9e67045945e49726fd812fcde39b")
    fun getPicsByTag(@Path("tag") tag: String): Call<ResponseInstaByTag>
}