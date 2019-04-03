package com.corewillsoft.posts.domain.post.usecase

import com.corewillsoft.posts.domain.UseCase
import com.corewillsoft.posts.domain.post.model.Post
import com.corewillsoft.posts.domain.post.repository.FavoriteRepository
import com.corewillsoft.posts.domain.post.usecase.TogglePostIsFavoriteUseCase.InParams
import io.reactivex.Completable
import javax.inject.Inject

class TogglePostIsFavoriteUseCase @Inject constructor(private val repository: FavoriteRepository): UseCase<InParams, Completable> {

    override fun execute(param: InParams): Completable {
        if (param.favorite) {
            repository.removePostId(param.id)
        } else {
            repository.addPostId(param.id)
        }
        return Completable.complete()
    }

    data class InParams(val id: Int, val favorite: Boolean)
}