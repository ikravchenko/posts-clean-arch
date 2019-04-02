package com.corewillsoft.posts.remote.comment

import com.corewillsoft.posts.remote.comment.model.ApiComment
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CommentApi {

    @GET("comments")
    fun fetchComments(@Query("postId") postId: Int): Single<List<ApiComment>>
}