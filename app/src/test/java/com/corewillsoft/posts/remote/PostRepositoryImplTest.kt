package com.corewillsoft.posts.remote

import com.corewillsoft.posts.domain.post.model.Post
import com.corewillsoft.posts.remote.post.PostApi
import com.corewillsoft.posts.remote.post.mapper.ApiPostMapper
import com.corewillsoft.posts.remote.post.model.ApiPost
import com.corewillsoft.posts.remote.post.repository.PostRepositoryImpl
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.kotlintest.specs.WordSpec
import io.reactivex.Single
import java.io.IOException

class PostRepositoryImplTest : WordSpec() {

    init {
        "getPosts" should {
            "call api and complete with result if call succeeds" {
                val serviceApi = mock<PostApi> {
                    on { fetchPosts(any()) } doReturn Single.just(
                        listOf(
                            ApiPost(
                                userId = 1,
                                id = 1,
                                title = "Title",
                                body = "Body"
                            )
                        )
                    )
                }
                val repository = PostRepositoryImpl(
                    postApi = serviceApi,
                    mapper = ApiPostMapper()
                )

                repository.getPosts(1)
                    .test()
                    .assertNoErrors()
                    .assertComplete()
                    .assertValue(listOf(Post(userId = 1, id = 1, title = "Title", body = "Body", favorite = false)))

                verify(serviceApi).fetchPosts(1)

            }

            "call api and throw error if call fails" {
                val serviceApi = mock<PostApi> {
                    on { fetchPosts(any()) } doReturn Single.error(IOException("No internet"))
                }
                val repository = PostRepositoryImpl(
                    postApi = serviceApi,
                    mapper = ApiPostMapper()
                )

                repository.getPosts(1)
                    .test()
                    .assertError(IOException::class.java)
                    .assertNotComplete()

                verify(serviceApi).fetchPosts(1)
            }
        }
    }
}