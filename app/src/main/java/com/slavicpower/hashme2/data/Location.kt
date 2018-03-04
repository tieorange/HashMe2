package com.slavicpower.hashme2.data


import com.google.gson.annotations.SerializedName


data class Location(

	@field:SerializedName("latitude")
	val latitude: Double? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("longitude")
	val longitude: Double? = null
)