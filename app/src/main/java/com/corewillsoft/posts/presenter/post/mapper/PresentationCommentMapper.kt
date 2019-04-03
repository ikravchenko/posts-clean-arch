package com.corewillsoft.posts.presenter.post.mapper

import com.corewillsoft.posts.domain.Mapper
import com.corewillsoft.posts.domain.comment.model.Comment
import com.corewillsoft.posts.presenter.post.model.PresentationComment
import javax.inject.Inject

class PresentationCommentMapper @Inject constructor() :
    Mapper<Comment, PresentationComment> {

    override fun map(from: Comment) = with(from) {
        PresentationComment(
            name = name,
            body = body,
            email = email
        )
    }
}