package com.corewillsoft.posts.remote.comment.repository

import com.corewillsoft.posts.domain.comment.repository.CommentRepository
import com.corewillsoft.posts.remote.comment.CommentApi
import com.corewillsoft.posts.remote.comment.mapper.ApiCommentMapper
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val commentApi: CommentApi,
    private val mapper: ApiCommentMapper
) : CommentRepository {

    override fun getComments(postId: Int) = commentApi.fetchComments(postId)
        .map { it.map(mapper::map) }

}