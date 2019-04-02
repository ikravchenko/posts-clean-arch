package com.corewillsoft.posts.domain.user.usecase

import com.corewillsoft.posts.domain.user.repository.UserRepository
import com.nhaarman.mockitokotlin2.verify
import io.kotlintest.mock.mock
import io.kotlintest.specs.StringSpec

class LoginUseCaseTest : StringSpec() {

    init {
        "saves user id on login" {
            val repository = mock<UserRepository>()
            LoginUseCase(repository = repository).execute(LoginUseCase.InParams(id = 1))
                .test()
                .assertNoErrors()
                .assertComplete()

            verify(repository).id = 1
        }
    }
}