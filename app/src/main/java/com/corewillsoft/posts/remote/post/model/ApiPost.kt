package com.corewillsoft.posts.remote.post.model

import com.squareup.moshi.Json

data class ApiPost(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "body")
    val body: String,
    @Json(name = "userId")
    val userId: Int
)