package com.corewillsoft.posts.presenter.post.list.view

import com.corewillsoft.posts.presenter.post.model.PresentationPost

/**
 * Interface to interact with user's list of posts
 */
interface PostsView {

    fun showPosts(posts: List<PresentationPost>)

    fun showLoadPostsError()

    fun showProgress()

    fun hideProgress()

    fun navigateToLogin()

    fun navigateToDetails(post: PresentationPost)
}