package com.slavicpower.hashme2.data


import com.google.gson.annotations.SerializedName


data class DataItem(
        @field:SerializedName("images")
        val images: Images
)