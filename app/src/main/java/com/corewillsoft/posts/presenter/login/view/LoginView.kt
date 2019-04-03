package com.corewillsoft.posts.presenter.login.view

/**
 * Interface to interact with a view in login
 */
interface LoginView {

    var loginEnabled: Boolean

    fun navigateToPosts()
}