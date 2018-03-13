package com.slavicpower.hashme2.data

import com.google.gson.annotations.SerializedName

data class ResponseInstaByTag(
        @field:SerializedName("data")
        val data: List<DataItem>
)