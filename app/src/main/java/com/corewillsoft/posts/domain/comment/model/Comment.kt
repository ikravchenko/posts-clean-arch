package com.corewillsoft.posts.domain.comment.model

/**
 * Model for a comment under the post. There can be multiple comments related to one post
 *
 * @see com.corewillsoft.posts.domain.post.model.Post
 */
data class Comment(
    val postId: Int,
    val id: Int,
    val name: String,
    val body: String,
    val email: String
)

