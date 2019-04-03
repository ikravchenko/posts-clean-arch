package com.corewillsoft.posts.domain.post.repository

import com.corewillsoft.posts.domain.post.model.Post
import io.reactivex.Single

/**
 * Interface to interact with [Post]s
 */
interface PostRepository {

    fun getPosts(userId: Int): Single<List<Post>>
}