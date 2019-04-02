package com.corewillsoft.posts.domain.post.repository

import com.corewillsoft.posts.domain.post.model.Post
import io.reactivex.Single

interface PostRepository {

    fun getPosts(userId: Int): Single<List<Post>>
}