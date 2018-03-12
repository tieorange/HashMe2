package com.slavicpower.hashme2

import com.slavicpower.hashme2.data.ResponseInstaByTag
import retrofit2.Call

class InstaDownloader() {

    fun getPicsByTag(tag: String, onFinished: (r: List<String?>?) -> Unit) {
        MyApp.myApiService.getPicsByTag(tag).enqueue(object : retrofit2.Callback<ResponseInstaByTag> {

            override fun onResponse(
                    call: Call<ResponseInstaByTag>?,
                    response: retrofit2.Response<ResponseInstaByTag>?
            ) {
                val linksList: List<String?>? = response?.body()?.data?.map {
                    // IMPORTANT:
                    it?.images?.standardResolution?.url
                }
                response?.body()?.let { onFinished(linksList) }
            }

            override fun onFailure(call: Call<ResponseInstaByTag>?, t: Throwable?) {
            }
        })
    }
}