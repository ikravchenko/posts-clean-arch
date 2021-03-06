package com.corewillsoft.posts.domain.user.usecase

import com.corewillsoft.posts.domain.NonArgUseCase
import com.corewillsoft.posts.domain.post.repository.FavoriteRepository
import com.corewillsoft.posts.domain.user.repository.UserRepository
import io.reactivex.Completable
import javax.inject.Inject

/**
 * Logs user out of the app and wipes all user related data
 *
 * @see LoginUseCase
 * @see GetCurrentUserIdUseCase
 */
class LogoutUseCase @Inject constructor(private val userRepository: UserRepository,
                                        private val favoriteRepository: FavoriteRepository) : NonArgUseCase<Completable> {

    override fun execute(): Completable {
        userRepository.id = null
        favoriteRepository.clear()
        return Completable.complete()
    }
}