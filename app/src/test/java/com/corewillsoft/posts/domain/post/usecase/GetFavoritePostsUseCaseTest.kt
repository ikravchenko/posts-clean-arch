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

            val favoritesRepository = mock<FavoriteRepository> {
                on { favoritePostIds } doReturn Single.just(emptySet())
            }
            val getAllPostsUseCase = mock<GetAllPostsUseCase> {
                on { execute(any()) } doReturn Single.just(listOf<Post>(mock(), mock(), mock()))
            }
            GetFavoritePostsUseCase(
                getAllPostsUseCase = getAllPostsUseCase,
                favoriteRepository = favoritesRepository
            ).execute(GetFavoritePostsUseCase.InParams(userId = 1))
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue(emptyList())

            verify(favoritesRepository).favoritePostIds
            verify(getAllPostsUseCase).execute(GetAllPostsUseCase.InParams(userId = 1))
        }

        "gets favorite posts if there are saved favorites" {

            val favoritesRepository = mock<FavoriteRepository> {
                on { favoritePostIds } doReturn Single.just(setOf(1))
            }
            val getAllPostsUseCase = mock<GetAllPostsUseCase> {
                on { execute(any()) } doReturn Single.just(
                    listOf(
                        Post(userId = 1, id = 1, title = "Title", body = "Body"),
                        Post(userId = 1, id = 2, title = "Title1", body = "Body1")
                    )
                )
            }
            GetFavoritePostsUseCase(
                getAllPostsUseCase = getAllPostsUseCase,
                favoriteRepository = favoritesRepository
            ).execute(GetFavoritePostsUseCase.InParams(userId = 1))
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue(listOf(Post(userId = 1, id = 1, title = "Title", body = "Body", favorite = true)))

            verify(favoritesRepository).favoritePostIds
            verify(getAllPostsUseCase).execute(GetAllPostsUseCase.InParams(userId = 1))
        }
    }
}