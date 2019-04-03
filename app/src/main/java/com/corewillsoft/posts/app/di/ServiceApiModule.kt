package com.corewillsoft.posts.app.di

import com.corewillsoft.posts.remote.comment.CommentApi
import com.corewillsoft.posts.remote.post.PostApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = [RestClientModule::class])
class ServiceApiModule {

    @Provides
    fun providePostApi(retrofit: Retrofit) = retrofit.create(PostApi::class.java)

    @Provides
    fun provideCommentApi(retrofit: Retrofit) = retrofit.create(CommentApi::class.java)
}