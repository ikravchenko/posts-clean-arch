package com.corewillsoft.posts.domain.post.repository

import io.reactivex.Completable
import io.reactivex.Single

interface FavoritesRepository {

    val favoritePostIds: Single<Set<Int>>

    fun addPostId(id: Int): Completable

    fun removePostId(id: Int): Completable
}