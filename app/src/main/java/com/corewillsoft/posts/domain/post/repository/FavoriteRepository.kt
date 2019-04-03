package com.corewillsoft.posts.domain.post.repository

interface FavoriteRepository {

    val favoritePostIds: Set<Int>

    fun addPostId(id: Int)

    fun removePostId(id: Int)

    fun clear()
}