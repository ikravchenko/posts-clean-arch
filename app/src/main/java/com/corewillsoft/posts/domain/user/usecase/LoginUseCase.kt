package com.corewillsoft.posts.domain.user.usecase

import com.corewillsoft.posts.domain.UseCase
import com.corewillsoft.posts.domain.user.repository.UserRepository
import com.corewillsoft.posts.domain.user.usecase.LoginUseCase.InParams
import io.reactivex.Completable
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: UserRepository): UseCase<InParams, Completable> {
    override fun execute(param: InParams): Completable {
        repository.id = param.id
        return Completable.complete()
    }

    data class InParams(val id: Int)
}