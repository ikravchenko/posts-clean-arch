package com.corewillsoft.posts.domain.user.usecase

import com.corewillsoft.posts.domain.NonArgUseCase
import com.corewillsoft.posts.domain.user.repository.UserRepository
import io.reactivex.Completable
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val repository: UserRepository) : NonArgUseCase<Completable> {

    override fun execute(): Completable {
        repository.id = null
        return Completable.complete()
    }
}