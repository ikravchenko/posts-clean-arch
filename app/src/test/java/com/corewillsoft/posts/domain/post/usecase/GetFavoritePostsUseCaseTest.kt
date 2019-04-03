package com.corewillsoft.posts.domain.post.usecase

import com.corewillsoft.posts.domain.post.model.Post
import com.corewillsoft.posts.domain.post.repository.FavoriteRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.kotlintest.mock.mock
import io.kotlintest.specs.StringSpec
import io.reactivex.Single

class GetFavoritePostsUseCaseTest : StringSpec() {

    init {
        "gets empty favorite posts if there are no favorites" {
            val getAllPostsUseCase = mock<GetAllPostsUseCase> {
                on { execute() } doReturn Single.just(
                    listOf(
                        Post(userId = 1, id = 1, title = "Title", body = "Body", favorite = false),
                        Post(userId = 1, id = 2, title = "Title1", body = "Body1", favorite = false)
                    )
                )
            }
            GetFavoritePostsUseCase(getAllPostsUseCase = getAllPostsUseCase)
                .execute()
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue(emptyList())

            verify(getAllPostsUseCase).execute()
        }

        "gets favorite posts if there are saved favorites" {
            val getAllPostsUseCase = mock<GetAllPostsUseCase> {
                on { execute() } doReturn Single.just(
                    listOf(
                        Post(userId = 1, id = 1, title = "Title", body = "Body", favorite = true),
                        Post(userId = 1, id = 2, title = "Title1", body = "Body1", favorite = false)
                    )
                )
            }
            GetFavoritePostsUseCase(getAllPostsUseCase = getAllPostsUseCase)
                .execute()
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue(listOf(Post(userId = 1, id = 1, title = "Title", body = "Body", favorite = true)))

            verify(getAllPostsUseCase).execute()
        }
    }
}