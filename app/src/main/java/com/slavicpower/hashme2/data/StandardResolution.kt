package com.slavicpower.hashme2.data


import com.google.gson.annotations.SerializedName


data class StandardResolution(

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("height")
	val height: Int? = null
)