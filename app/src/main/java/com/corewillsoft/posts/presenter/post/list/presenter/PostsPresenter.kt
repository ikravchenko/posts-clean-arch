package com.corewillsoft.posts.presenter.post.list.presenter

import com.corewillsoft.posts.app.di.ObserveOnScheduler
import com.corewillsoft.posts.presenter.PresenterLifecycle
import com.corewillsoft.posts.presenter.login.interactor.UserInteractor
import com.corewillsoft.posts.presenter.post.list.view.PostsView
import com.corewillsoft.posts.presenter.post.list.interactor.PostInteractor
import com.corewillsoft.posts.presenter.post.model.PresentationPost
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Presents posts list of the current user
 *
 * @see PostsView
 */
interface PostsPresenter : PresenterLifecycle {

    fun onAllClicked()

    fun onFavoriteClicked()

    fun onPostIsFavoriteToggled(postId: Int, favorite: Boolean)

    fun onLogoutClicked()

    fun onPostClicked(post: PresentationPost)
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

    override fun onPostClicked(post: PresentationPost) {
        view.navigateToDetails(post)
    }

    override fun start() {
    }

    override fun stop() {
        disposables.clear()
    }

}