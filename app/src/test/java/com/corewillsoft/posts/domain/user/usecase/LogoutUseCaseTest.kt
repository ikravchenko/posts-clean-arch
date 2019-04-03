package com.corewillsoft.posts.domain.user.usecase

import com.corewillsoft.posts.domain.post.repository.FavoriteRepository
import com.corewillsoft.posts.domain.user.repository.UserRepository
import com.nhaarman.mockitokotlin2.verify
import io.kotlintest.mock.mock
import io.kotlintest.specs.StringSpec

class LogoutUseCaseTest : StringSpec() {

    init {
        "clears user id and favorites on logout" {
            val userRepository = mock<UserRepository>()
            val favoriteRepository = mock<FavoriteRepository>()
            LogoutUseCase(
                userRepository = userRepository,
                favoriteRepository = favoriteRepository
            ).execute()
                .test()
                .assertNoErrors()
                .assertComplete()

            verify(userRepository).id = null
            verify(favoriteRepository).clear()
        }
    }
}