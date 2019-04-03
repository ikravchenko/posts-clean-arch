package com.corewillsoft.posts.presenter.post

import com.corewillsoft.posts.domain.Mapper
import com.corewillsoft.posts.domain.post.model.Post
import javax.inject.Inject

class PresentationPostMapper @Inject constructor() :
    Mapper<Post, PresentationPost> {

    override fun map(from: Post) = with(from) {
        PresentationPost(
            id = id,
            body = body,
            favorite = favorite,
            title = title
        )
    }

}