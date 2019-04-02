package com.corewillsoft.posts.domain.comment.usecase

import com.corewillsoft.posts.domain.UseCase
import com.corewillsoft.posts.domain.comment.model.Comment
import com.corewillsoft.posts.domain.comment.repository.CommentRepository
import com.corewillsoft.posts.domain.comment.usecase.GetCommentsUseCase.InParams
import io.reactivex.Single
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(private val repository: CommentRepository) : UseCase<InParams, Single<List<Comment>>> {

    override fun execute(param: InParams): Single<List<Comment>> = repository.getComments(param.postId)

    data class InParams(val postId: Int)
}