package com.corewillsoft.posts.presenter.post.mapper

import com.corewillsoft.posts.common.Mapper
import com.corewillsoft.posts.domain.post.model.Post
import com.corewillsoft.posts.presenter.post.model.PresentationPost
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