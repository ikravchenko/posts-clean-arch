package com.corewillsoft.posts.presenter.post

import com.corewillsoft.posts.app.di.ObserveOnScheduler
import com.corewillsoft.posts.presenter.PresenterLifecycle
import com.corewillsoft.posts.presenter.login.UserInteractor
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface PostsPresenter : PresenterLifecycle {

    fun onAllClicked()

    fun onFavoriteClicked()

    fun onPostIsFavoriteToggled(postId: Int, favorite: Boolean)

    fun onLogoutClicked()
}

class PostsPresenterImpl @Inject constructor(
    private val view: PostsView,
    private val userInteractor: UserInteractor,
    private val postInteractor: PostInteractor,
    @ObserveOnScheduler private val observeOnScheduler: Scheduler
) : PostsPresenter {

    private var showFavoritesOnly = false

    private val disposables: CompositeDisposable by lazy { CompositeDisposable() }

    override fun onAllClicked() {
        showFavoritesOnly = false
        loadAll()
    }

    private fun loadAll() {
        view.showProgress()
        disposables += postInteractor.allPosts
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
        showFavoritesOnly = true
        loadFavorites()
    }

    private fun loadFavorites() {
        view.showProgress()
        disposables += postInteractor.favoritePosts
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

    override fun onPostIsFavoriteToggled(postId: Int, favorite: Boolean) {
        disposables += postInteractor.togglePostIsFavorite(postId, favorite)
            .doOnComplete {
                if (showFavoritesOnly) {
                    loadFavorites()
                } else {
                    loadAll()
                }
            }
            .subscribeBy(onComplete = {
                //do nothing
            }, onError = {
                //do nothing
            })
    }

    override fun onLogoutClicked() {
        disposables += userInteractor.logout()
            .subscribeBy(
                onComplete = { view.navigateToLogin() },
                onError = {
                    //do nothing
                })
    }

    override fun start() {
    }

    override fun stop() {
        disposables.clear()
    }

}