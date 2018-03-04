package com.slavicpower.hashme2.data

import com.google.gson.annotations.SerializedName

data class ResponseInstaByTag(

        @field:SerializedName("pagination")
        val pagination: Pagination? = null,

        @field:SerializedName("data")
        val data: List<DataItem?>? = null,

        @field:SerializedName("meta")
        val meta: Meta? = null
)