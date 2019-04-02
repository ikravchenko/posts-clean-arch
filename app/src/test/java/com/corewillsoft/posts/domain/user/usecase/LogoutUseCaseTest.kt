package com.corewillsoft.posts.domain.user.usecase

import com.corewillsoft.posts.domain.user.repository.UserRepository
import com.nhaarman.mockitokotlin2.verify
import io.kotlintest.mock.mock
import io.kotlintest.specs.StringSpec

class LogoutUseCaseTest: StringSpec() {

    init {
        "clears user id on logout" {
            val repository = mock<UserRepository>()
            LogoutUseCase(repository = repository).execute()
                .test()
                .assertNoErrors()
                .assertComplete()

            verify(repository).id = null
        }
    }
}