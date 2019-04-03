package com.corewillsoft.posts.presenter.post

data class PresentationPost(
    val id: Int,
    val title: String,
    val body: String,
    val favorite: Boolean
)