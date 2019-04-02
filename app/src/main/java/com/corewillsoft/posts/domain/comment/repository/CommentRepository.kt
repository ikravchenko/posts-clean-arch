package com.corewillsoft.posts.domain.comment.repository

import com.corewillsoft.posts.domain.comment.model.Comment
import io.reactivex.Single

interface CommentRepository {

    fun getComments(postId: Int): Single<List<Comment>>
}