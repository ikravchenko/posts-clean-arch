package com.corewillsoft.posts.remote.post

import com.corewillsoft.posts.remote.post.model.ApiPost
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PostApi {

    @GET("posts")
    fun fetchPosts(@Query("userId") userId: Int): Single<List<ApiPost>>
}