package com.corewillsoft.posts.presenter.post.detail

import com.corewillsoft.posts.domain.comment.usecase.GetCommentsUseCase
import com.corewillsoft.posts.presenter.post.mapper.PresentationCommentMapper
import com.corewillsoft.posts.presenter.post.model.PresentationComment
import io.reactivex.Single
import javax.inject.Inject

interface CommentInteractor {

    fun getCommentsForPost(postId: Int): Single<List<PresentationComment>>
}

class CommentInteractorImpl @Inject constructor(
    private val getCommentsUseCase: GetCommentsUseCase,
    private val mapper: PresentationCommentMapper
) :
    CommentInteractor {

    override fun getCommentsForPost(postId: Int) =
        getCommentsUseCase.execute(GetCommentsUseCase.InParams(postId))
            .map { it.map(mapper::map) }
}