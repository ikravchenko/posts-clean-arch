package com.corewillsoft.posts.domain.post.repository

import io.reactivex.Completable
import io.reactivex.Single

interface FavoriteRepository {

    val favoritePostIds: Single<Set<Int>>

    fun addPostId(id: Int): Completable

    fun removePostId(id: Int): Completable
}