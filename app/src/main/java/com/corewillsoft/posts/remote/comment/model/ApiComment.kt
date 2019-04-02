package com.corewillsoft.posts.remote.comment.model

import com.squareup.moshi.Json

data class ApiComment(
    @Json(name = "name")
    val name: String,
    @Json(name = "postId")
    val postId: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "body")
    val body: String,
    @Json(name = "email")
    val email: String
)