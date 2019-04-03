package com.corewillsoft.posts.domain.post.usecase

import com.corewillsoft.posts.domain.post.repository.FavoriteRepository
import com.corewillsoft.posts.domain.post.usecase.TogglePostIsFavoriteUseCase.*
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.kotlintest.specs.StringSpec

class TogglePostIsFavoriteUseCaseTest : StringSpec() {

    init {

        val repository = mock<FavoriteRepository>()
        val useCase = TogglePostIsFavoriteUseCase(repository = repository)

        "removes post from favorites if post is favorite" {
            useCase.execute(InParams(1,true))
                .test()
                .assertComplete()

            verify(repository).removePostId(1)
        }

        "adds post to favorites if post is not favorite" {
            useCase.execute(InParams(1,false))
                .test()
                .assertComplete()

            verify(repository).addPostId(1)
        }
    }
}