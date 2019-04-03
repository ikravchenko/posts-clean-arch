package com.corewillsoft.posts.domain.post.usecase

import com.corewillsoft.posts.domain.NonArgUseCase
import com.corewillsoft.posts.domain.UseCase
import com.corewillsoft.posts.domain.post.model.Post
import com.corewillsoft.posts.domain.post.repository.FavoriteRepository
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

/**
 * Gets all favorite posts of the logged in user
 *
 * @see GetAllPostsUseCase
 * @see TogglePostIsFavoriteUseCase
 */
class GetFavoritePostsUseCase @Inject constructor(
    private val getAllPostsUseCase: GetAllPostsUseCase
) : NonArgUseCase<Single<List<Post>>> {

    override fun execute(): Single<List<Post>> {
        return getAllPostsUseCase.execute()
            .map { it.filter(Post::favorite) }
    }
}