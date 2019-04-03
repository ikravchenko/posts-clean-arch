package com.corewillsoft.posts.presenter.post.list

import com.corewillsoft.posts.presenter.post.model.PresentationPost

interface PostsView {

    fun showPosts(posts: List<PresentationPost>)

    fun showLoadPostsError()

    fun showProgress()

    fun hideProgress()

    fun navigateToLogin()

    fun navigateToDetails(post: PresentationPost)
}