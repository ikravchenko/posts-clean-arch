package com.corewillsoft.posts.domain.post.usecase

import com.corewillsoft.posts.domain.NonArgUseCase
import com.corewillsoft.posts.domain.post.model.Post
import com.corewillsoft.posts.domain.post.repository.FavoriteRepository
import com.corewillsoft.posts.domain.post.repository.PostRepository
import com.corewillsoft.posts.domain.user.repository.UserRepository
import io.reactivex.Single
import java.lang.Exception
import javax.inject.Inject

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
        } ?: Single.error(Exception("no user id saved to get posts for!"))
}

