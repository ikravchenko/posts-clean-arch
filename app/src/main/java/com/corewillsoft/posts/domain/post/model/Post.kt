package com.corewillsoft.posts.domain.post.model

/**
 * Model representing a message posted by the user. Post can have comments
 *
 * @see com.corewillsoft.posts.domain.comment.model.Comment
 */
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
    val favorite: Boolean = false
)