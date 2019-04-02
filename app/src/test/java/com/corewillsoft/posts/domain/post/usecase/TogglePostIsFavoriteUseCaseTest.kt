package com.corewillsoft.posts.domain.post.usecase

import com.corewillsoft.posts.domain.post.model.Post
import com.corewillsoft.posts.domain.post.repository.FavoriteRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.kotlintest.specs.StringSpec
import io.reactivex.Completable

class TogglePostIsFavoriteUseCaseTest : StringSpec() {

    init {

        val repository = mock<FavoriteRepository> {
            on { addPostId(any()) } doReturn Completable.complete()
            on { removePostId(any()) } doReturn Completable.complete()
        }
        val useCase = TogglePostIsFavoriteUseCase(repository = repository)

        "removes post from favorites if post is favorite" {
            useCase.execute(Post(userId = 1, id = 1, title = "T", body = "B", favorite = true))
                .test()
                .assertComplete()

            verify(repository).removePostId(1)
        }

        "adds post to favorites if post is not favorite" {
            useCase.execute(Post(userId = 1, id = 1, title = "T", body = "B", favorite = false))
                .test()
                .assertComplete()

            verify(repository).addPostId(1)
        }
    }
}