package com.corewillsoft.posts.presenter.login

interface LoginView {

    var loginEnabled: Boolean

    fun navigateToPosts()
}