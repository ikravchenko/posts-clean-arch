package com.corewillsoft.posts.remote.post.repository

import com.corewillsoft.posts.domain.post.repository.PostRepository
import com.corewillsoft.posts.remote.post.PostApi
import com.corewillsoft.posts.remote.post.mapper.ApiPostMapper
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postApi: PostApi,
    private val mapper: ApiPostMapper
) : PostRepository {

    override fun getPosts(userId: Int) = postApi.fetchPosts(userId).map { it.map(mapper::map) }

}