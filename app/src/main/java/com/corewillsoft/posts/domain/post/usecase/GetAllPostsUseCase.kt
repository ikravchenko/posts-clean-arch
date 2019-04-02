package com.corewillsoft.posts.domain.post.usecase

import com.corewillsoft.posts.domain.UseCase
import com.corewillsoft.posts.domain.post.model.Post
import com.corewillsoft.posts.domain.post.repository.PostRepository
import io.reactivex.Single
import javax.inject.Inject

class GetAllPostsUseCase @Inject constructor(private val repository: PostRepository) :
    UseCase<GetAllPostsUseCase.InParams, Single<List<Post>>> {

    override fun execute(param: InParams): Single<List<Post>> = repository.getPosts(param.userId, false)

    data class InParams(val userId: Int)
}