package com.corewillsoft.posts.presenter.post

interface PostsView {

    fun showPosts(posts: List<PresentationPost>)

    fun showLoadPostsError()

    fun showProgress()

    fun hideProgress()

    fun navigateToLogin()

    fun navigateToComments(postId: Int)
}