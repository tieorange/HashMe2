package com.slavicpower.hashme2.data


import com.google.gson.annotations.SerializedName


data class Images(
        @field:SerializedName("standard_resolution")
        val standardResolution: StandardResolution
)