package com.corewillsoft.posts.domain.comment.model

data class Comment(
    val postId: Int,
    val id: Int,
    val name: String,
    val body: String,
    val email: String
)

