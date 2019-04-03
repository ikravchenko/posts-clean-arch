package com.corewillsoft.posts.presenter.post.detail.view

import com.corewillsoft.posts.presenter.post.model.PresentationComment
import com.corewillsoft.posts.presenter.post.model.PresentationPost

/**
 * Interface to interact with post details view
 */
interface PostDetailView {

    fun showPost(post: PresentationPost)

    fun showComments(comments: List<PresentationComment>)

    fun showLoadingError()

    fun showProgress()

    fun hideProgress()
}