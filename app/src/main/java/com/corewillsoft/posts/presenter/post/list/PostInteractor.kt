package com.corewillsoft.posts.presenter.post.list

import com.corewillsoft.posts.domain.post.usecase.GetAllPostsUseCase
import com.corewillsoft.posts.domain.post.usecase.GetFavoritePostsUseCase
import com.corewillsoft.posts.domain.post.usecase.TogglePostIsFavoriteUseCase
import com.corewillsoft.posts.presenter.post.mapper.PresentationPostMapper
import com.corewillsoft.posts.presenter.post.model.PresentationPost
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

interface PostInteractor {

    val allPosts: Single<List<PresentationPost>>

    val favoritePosts: Single<List<PresentationPost>>

    fun togglePostIsFavorite(id: Int, favorite: Boolean): Completable
}

class PostInteractorImpl @Inject constructor(
    private val getAllPostsUseCase: GetAllPostsUseCase,
    private val getFavoritePostsUseCase: GetFavoritePostsUseCase,
    private val togglePostIsFavoriteUseCase: TogglePostIsFavoriteUseCase,
    private val mapper: PresentationPostMapper
) : PostInteractor {

    override val allPosts: Single<List<PresentationPost>>
        get() = getAllPostsUseCase.execute().map { it.map(mapper::map) }
    override val favoritePosts: Single<List<PresentationPost>>
        get() = getFavoritePostsUseCase.execute().map { it.map(mapper::map) }

    override fun togglePostIsFavorite(id: Int, favorite: Boolean) =
        togglePostIsFavoriteUseCase.execute(TogglePostIsFavoriteUseCase.InParams(id, favorite))
}