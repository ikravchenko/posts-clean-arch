package com.corewillsoft.posts.domain.post.usecase

import com.corewillsoft.posts.domain.post.model.Post
import com.corewillsoft.posts.domain.post.repository.PostRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.kotlintest.mock.mock
import io.kotlintest.specs.StringSpec
import io.reactivex.Single

class GetAllPostsUseCaseTest: StringSpec() {

    init {
        "gets posts from repository" {
            val repository = mock<PostRepository>()
            val result = listOf(mock<Post>())
            whenever(repository.getPosts(any(), any())).thenReturn(Single.just(result))

            GetAllPostsUseCase(repository = repository).execute(GetAllPostsUseCase.InParams(userId = 1))
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValue(result)

            verify(repository).getPosts(1, false)
        }
    }
}