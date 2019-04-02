package com.corewillsoft.posts.domain.post.usecase

import com.corewillsoft.posts.domain.post.model.Post
import com.corewillsoft.posts.domain.post.repository.FavoriteRepository
import com.corewillsoft.posts.domain.post.repository.PostRepository
import com.nhaarman.mockitokotlin2.*
import io.kotlintest.mock.mock
import io.kotlintest.specs.StringSpec
import io.reactivex.Single

class GetAllPostsUseCaseTest : StringSpec() {

    init {
        "gets posts from repository with no favorites" {
            val postRepository = mock<PostRepository>()
            val favoriteRepository = mock<FavoriteRepository> {
                on { favoritePostIds } doReturn Single.just(emptySet())
            }
            whenever(postRepository.getPosts(any())).thenReturn(
                Single.just(
                    listOf(Post(userId = 1, id = 1, title = "Title", body = "Body"))
                )
            )

            GetAllPostsUseCase(postRepository = postRepository, favoriteRepository = favoriteRepository).execute(
                GetAllPostsUseCase.InParams(userId = 1)
            )
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue(listOf(Post(userId = 1, id = 1, title = "Title", body = "Body", favorite = false)))

            verify(postRepository).getPosts(1)
            verify(favoriteRepository).favoritePostIds
        }

        "gets posts from repository if there are favorites" {
            val postRepository = mock<PostRepository>()
            val favoriteRepository = mock<FavoriteRepository> {
                on { favoritePostIds } doReturn Single.just(setOf(1))
            }
            whenever(postRepository.getPosts(any())).thenReturn(
                Single.just(
                    listOf(
                        Post(userId = 1, id = 1, title = "Title", body = "Body"),
                        Post(userId = 1, id = 2, title = "Title1", body = "Body1")
                    )
                )
            )

            GetAllPostsUseCase(postRepository = postRepository, favoriteRepository = favoriteRepository).execute(
                GetAllPostsUseCase.InParams(userId = 1)
            )
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue(
                    listOf(
                        Post(userId = 1, id = 1, title = "Title", body = "Body", favorite = true),
                        Post(userId = 1, id = 2, title = "Title1", body = "Body1", favorite = false)
                    )
                )

            verify(postRepository).getPosts(1)
            verify(favoriteRepository).favoritePostIds
        }
    }
}