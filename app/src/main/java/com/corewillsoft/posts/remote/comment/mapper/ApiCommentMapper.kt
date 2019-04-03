package com.corewillsoft.posts.remote.comment.mapper

import com.corewillsoft.posts.common.Mapper
import com.corewillsoft.posts.domain.comment.model.Comment
import com.corewillsoft.posts.remote.comment.model.ApiComment
import javax.inject.Inject

class ApiCommentMapper @Inject constructor() :
    Mapper<ApiComment, Comment> {

    override fun map(from: ApiComment) = with(from) {
        Comment(
            postId = postId,
            id = id,
            body = body,
            email = email,
            name = name
        )
    }
}