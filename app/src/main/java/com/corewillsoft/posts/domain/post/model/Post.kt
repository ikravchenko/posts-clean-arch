package com.corewillsoft.posts.domain.post.model

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
    val favorite: Boolean = false
)