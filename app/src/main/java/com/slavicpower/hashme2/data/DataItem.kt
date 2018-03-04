package com.slavicpower.hashme2.data


import com.google.gson.annotations.SerializedName


data class DataItem(

        @field:SerializedName("created_time")
	val createdTime: String? = null,

        @field:SerializedName("images")
	val images: Images? = null,

        @field:SerializedName("comments")
	val comments: Comments? = null,

        @field:SerializedName("users_in_photo")
	val usersInPhoto: List<Any?>? = null,

        @field:SerializedName("user_has_liked")
	val userHasLiked: Boolean? = null,

        @field:SerializedName("link")
	val link: String? = null,

        @field:SerializedName("caption")
	val caption: Caption? = null,

        @field:SerializedName("type")
	val type: String? = null,

        @field:SerializedName("tags")
	val tags: List<String?>? = null,

        @field:SerializedName("filter")
	val filter: String? = null,

        @field:SerializedName("attribution")
	val attribution: Any? = null,

        @field:SerializedName("location")
	val location: Location? = null,

        @field:SerializedName("id")
	val id: String? = null,

        @field:SerializedName("user")
	val user: User? = null,

        @field:SerializedName("likes")
	val likes: Likes? = null
)