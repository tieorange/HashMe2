package com.slavicpower.hashme2

import com.slavicpower.hashme2.data.ResponseInstaByTag
import retrofit2.Response

class InstaDownloader {

    private fun onSuccess(
            response: Response<ResponseInstaByTag>?,
            onFinished: (r: List<String?>?) -> Unit
    ) {
        val linksList: List<String?>? = response?.body()?.data?.map {
            // IMPORTANT:
            it?.images?.standardResolution?.url
        }
        response?.body()?.let { onFinished(linksList) }
    }
}