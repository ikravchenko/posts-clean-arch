package com.corewillsoft.posts.remote.comment.repository

import com.corewillsoft.posts.domain.comment.model.Comment
import com.corewillsoft.posts.remote.comment.CommentApi
import com.corewillsoft.posts.remote.comment.mapper.ApiCommentMapper
import com.corewillsoft.posts.remote.comment.model.ApiComment
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.kotlintest.specs.WordSpec
import io.reactivex.Single
import java.io.IOException

class CommentRepositoryImplTest : WordSpec() {

    init {
        "getComments" should {
            "call api and complete with result if call succeeds" {
                val serviceApi = mock<CommentApi> {
                    on { fetchComments(any()) } doReturn Single.just(
                        listOf(
                            ApiComment(
                                postId = 1,
                                id = 1,
                                name = "Title",
                                body = "Body",
                                email = "john@doe.com"
                            )
                        )
                    )
                }
                val repository = CommentRepositoryImpl(
                    commentApi = serviceApi,
                    mapper = ApiCommentMapper()
                )

                repository.getComments(1)
                    .test()
                    .assertNoErrors()
                    .assertComplete()
                    .assertValue(
                        listOf(
                            Comment(
                                postId = 1,
                                id = 1,
                                name = "Title",
                                body = "Body",
                                email = "john@doe.com"
                            )
                        )
                    )

                verify(serviceApi).fetchComments(1)

            }

            "call api and throw error if call fails" {
                val serviceApi = mock<CommentApi> {
                    on { fetchComments(any()) } doReturn Single.error(IOException("No internet"))
                }
                val repository = CommentRepositoryImpl(
                    commentApi = serviceApi,
                    mapper = ApiCommentMapper()
                )

                repository.getComments(1)
                    .test()
                    .assertError(IOException::class.java)
                    .assertNotComplete()

                verify(serviceApi).fetchComments(1)
            }
        }
    }
}