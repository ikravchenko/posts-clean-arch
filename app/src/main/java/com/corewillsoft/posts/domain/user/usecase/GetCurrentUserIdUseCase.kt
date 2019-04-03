package com.corewillsoft.posts.domain.user.usecase

import com.corewillsoft.posts.domain.NonArgUseCase
import com.corewillsoft.posts.domain.user.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * Gets currently logged in user
 *
 * @see LoginUseCase
 * @see LogoutUseCase
 */
class GetCurrentUserIdUseCase @Inject constructor(private val repository: UserRepository): NonArgUseCase<Int?> {

    override fun execute(): Int? = repository.id

}