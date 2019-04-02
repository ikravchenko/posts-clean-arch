package com.corewillsoft.posts.domain.comment.usecase

import com.corewillsoft.posts.domain.comment.model.Comment
import com.corewillsoft.posts.domain.comment.repository.CommentRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.kotlintest.specs.StringSpec
import io.reactivex.Single

class GetCommentsUseCaseTest : StringSpec() {

    init {

        "gets comments for post" {
            val comments = listOf<Comment>(mock())
            val repository = mock<CommentRepository> {
                on { getComments(any()) } doReturn Single.just(comments)
            }

            GetCommentsUseCase(repository = repository).execute(GetCommentsUseCase.InParams(postId = 1))
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue(comments)

            verify(repository).getComments(postId = 1)
        }
    }
}