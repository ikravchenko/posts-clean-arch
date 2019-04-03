package com.corewillsoft.posts.presenter.post

import com.corewillsoft.posts.app.di.ObserveOnScheduler
import com.corewillsoft.posts.presenter.PresenterLifecycle
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface PostsPresenter : PresenterLifecycle {

    fun onAllClicked()

    fun onFavoriteClicked()

    fun onPostIsFavoriteToggled(postId: Int)

    fun onLogoutClicked()
}

class PostsPresenterImpl @Inject constructor(
    private val view: PostsView,
    private val interactor: PostInteractor,
    @ObserveOnScheduler private val observeOnScheduler: Scheduler
) : PostsPresenter {

    private val disposables: CompositeDisposable by lazy { CompositeDisposable() }

    override fun onAllClicked() {
        view.showProgress()
        disposables += interactor.allPosts
            .subscribeOn(Schedulers.io())
            .observeOn(observeOnScheduler)
            .subscribeBy(
                onSuccess = {
                    view.hideProgress()
                    view.showPosts(it)
                },
                onError = {
                    view.hideProgress()
                    view.showLoadPostsError()
                }
            )
    }

    override fun onFavoriteClicked() {
        view.showProgress()
        disposables += interactor.favoritePosts
            .subscribeOn(Schedulers.io())
            .observeOn(observeOnScheduler)
            .subscribeBy(
                onSuccess = {
                    view.hideProgress()
                    view.showPosts(it)
                },
                onError = {
                    view.hideProgress()
                    view.showLoadPostsError()
                }
            )
    }

    override fun onPostIsFavoriteToggled(postId: Int) {
    }

    override fun onLogoutClicked() {
    }

    override fun start() {
    }

    override fun stop() {
        disposables.clear()
    }

}