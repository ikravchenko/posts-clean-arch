package com.corewillsoft.posts.app.di

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

@Module
class RxModule {

    @Provides
    @ObserveOnScheduler
    fun provideObserveOnScheduler(): Scheduler = AndroidSchedulers.mainThread()
}