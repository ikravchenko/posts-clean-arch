package com.corewillsoft.posts.domain.post.repository

/**
 * Interface to interact with favorite states of a post
 *
 * @see com.corewillsoft.posts.domain.post.model.Post
 */
interface FavoriteRepository {

    val favoritePostIds: Set<Int>

    fun addPostId(id: Int)

    fun removePostId(id: Int)

    fun clear()
}