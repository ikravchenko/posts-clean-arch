package com.corewillsoft.posts.domain.post.usecase

import com.corewillsoft.posts.domain.UseCase
import com.corewillsoft.posts.domain.post.model.Post
import com.corewillsoft.posts.domain.post.repository.FavoriteRepository
import com.corewillsoft.posts.domain.post.repository.PostRepository
import com.corewillsoft.posts.domain.post.usecase.GetAllPostsUseCase.InParams
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GetAllPostsUseCase @Inject constructor(
    private val postRepository: PostRepository,
    private val favoriteRepository: FavoriteRepository
) : UseCase<InParams, Single<List<Post>>> {

    override fun execute(param: InParams): Single<List<Post>> =
        Single.zip(
            postRepository.getPosts(param.userId),
            favoriteRepository.favoritePostIds, BiFunction { posts, favoriteIds ->
                posts.map { it.copy(favorite = favoriteIds.contains(it.id)) }
            })

    data class InParams(val userId: Int)
}