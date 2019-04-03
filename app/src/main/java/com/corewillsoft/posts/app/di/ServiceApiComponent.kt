package com.corewillsoft.posts.app.di

import com.corewillsoft.posts.remote.comment.CommentApi
import com.corewillsoft.posts.remote.post.PostApi
import dagger.Component

@Component(modules = [ServiceApiModule::class])
interface ServiceApiComponent {

    fun postApi(): PostApi

    fun commentApi(): CommentApi
}