package com.corewillsoft.posts.domain.post.usecase

import com.corewillsoft.posts.domain.NonArgUseCase
import com.corewillsoft.posts.domain.post.model.Post
import com.corewillsoft.posts.domain.post.repository.FavoriteRepository
import com.corewillsoft.posts.domain.post.repository.PostRepository
import com.corewillsoft.posts.domain.user.repository.UserRepository
import io.reactivex.Single
import java.lang.Exception
import javax.inject.Inject

/**
 * Gets all posts for a logged in user
 *
 * @see GetFavoritePostsUseCase
 * @see TogglePostIsFavoriteUseCase
 */
class GetAllPostsUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
    private val favoriteRepository: FavoriteRepository
) : NonArgUseCase<Single<List<Post>>> {

    override fun execute(): Single<List<Post>> =
        userRepository.id?.let { id ->
            postRepository.getPosts(id)
                .map { posts ->
                    val favoriteIds = favoriteRepository.favoritePostIds
                    posts.map { it.copy(favorite = favoriteIds.contains(it.id)) }
                }
        } ?: Single.error(NotAuthorizedException())
}

/**
 * Thrown when trying to get posts when there is no logged in user
 */
class NotAuthorizedException: Exception()

