package com.slavicpower.hashme2.data


import com.google.gson.annotations.SerializedName


data class Pagination(

	@field:SerializedName("next_min_id")
	val nextMinId: String? = null,

	@field:SerializedName("min_tag_id")
	val minTagId: String? = null,

	@field:SerializedName("deprecation_warning")
	val deprecationWarning: String? = null
)