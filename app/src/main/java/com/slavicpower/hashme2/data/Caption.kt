package com.slavicpower.hashme2.data

import com.google.gson.annotations.SerializedName


data class Caption(

        @field:SerializedName("created_time")
        val createdTime: String? = null,

        @field:SerializedName("from")
        val from: From? = null,

        @field:SerializedName("id")
        val id: String? = null,

        @field:SerializedName("text")
        val text: String? = null
)