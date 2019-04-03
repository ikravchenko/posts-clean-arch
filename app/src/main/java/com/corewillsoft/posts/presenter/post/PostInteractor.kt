package com.corewillsoft.posts.presenter.post

import com.corewillsoft.posts.domain.post.usecase.GetAllPostsUseCase
import com.corewillsoft.posts.domain.post.usecase.GetFavoritePostsUseCase
import io.reactivex.Single
import javax.inject.Inject

interface PostInteractor {

    val allPosts: Single<List<PresentationPost>>

    val favoritePosts: Single<List<PresentationPost>>
}

class PostInteractorImpl @Inject constructor(
    private val getAllPostsUseCase: GetAllPostsUseCase,
    private val getFavoritePostsUseCase: GetFavoritePostsUseCase,
    private val mapper: PresentationPostMapper
) : PostInteractor {
    override val allPosts: Single<List<PresentationPost>>
        get() = getAllPostsUseCase.execute().map { it.map(mapper::map) }
    override val favoritePosts: Single<List<PresentationPost>>
        get() = getFavoritePostsUseCase.execute().map { it.map(mapper::map) }
}