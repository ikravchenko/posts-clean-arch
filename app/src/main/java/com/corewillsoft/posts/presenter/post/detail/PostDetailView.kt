package com.corewillsoft.posts.presenter.post.detail

import com.corewillsoft.posts.presenter.post.model.PresentationComment
import com.corewillsoft.posts.presenter.post.model.PresentationPost

interface PostDetailView {

    fun showPost(post: PresentationPost)

    fun showComments(comments: List<PresentationComment>)

    fun showLoadingError()

    fun showProgress()

    fun hideProgress()
}