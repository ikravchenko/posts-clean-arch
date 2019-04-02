package com.corewillsoft.posts.remote.post.mapper

import com.corewillsoft.posts.domain.Mapper
import com.corewillsoft.posts.domain.post.model.Post
import com.corewillsoft.posts.remote.post.model.ApiPost
import javax.inject.Inject

class ApiPostMapper @Inject constructor() :
    Mapper<ApiPost, Post> {

    override fun map(from: ApiPost) = with(from) {
        Post(
            userId = userId,
            id = id,
            title = title,
            body = body,
            favorite = false
        )
    }
}