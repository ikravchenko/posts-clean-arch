package com.corewillsoft.posts.domain.post.usecase

import com.corewillsoft.posts.domain.UseCase
import com.corewillsoft.posts.domain.post.model.Post
import com.corewillsoft.posts.domain.post.repository.FavoritesRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GetFavoritePostsUseCase @Inject constructor(
    private val getAllPostsUseCase: GetAllPostsUseCase,
    private val favoritesRepository: FavoritesRepository
) : UseCase<GetFavoritePostsUseCase.InParams, Single<List<Post>>> {

    override fun execute(param: InParams): Single<List<Post>> {
        return Single.zip(
            getAllPostsUseCase.execute(GetAllPostsUseCase.InParams(userId = param.userId)),
            favoritesRepository.favoritePostIds, BiFunction { posts, favIds ->
                posts.filter { favIds.contains(it.id) }.map { it.copy(favorite = true) }
            })
    }

    data class InParams(val userId: Int)
}