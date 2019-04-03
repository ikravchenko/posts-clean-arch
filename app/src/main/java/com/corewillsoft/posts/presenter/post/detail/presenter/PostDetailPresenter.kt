package com.corewillsoft.posts.presenter.post.detail.presenter

import com.corewillsoft.posts.app.di.ObserveOnScheduler
import com.corewillsoft.posts.presenter.PresenterLifecycle
import com.corewillsoft.posts.presenter.post.detail.view.PostDetailView
import com.corewillsoft.posts.presenter.post.detail.interactor.CommentInteractor
import com.corewillsoft.posts.presenter.post.model.PresentationPost
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Presents post details
 *
 * @see PostDetailView
 */
interface PostDetailPresenter : PresenterLifecycle

class PostDetailPresenterImpl @Inject constructor(
    private val state: PresenterState,
    private val view: PostDetailView,
    private val interactor: CommentInteractor,
    @ObserveOnScheduler private val observeOnScheduler: Scheduler
) : PostDetailPresenter {

    data class PresenterState(val post: PresentationPost)

    private val disposables: CompositeDisposable by lazy { CompositeDisposable() }

    override fun start() {
        view.showPost(state.post)
        view.showProgress()
        disposables += interactor.getCommentsForPost(state.post.id)
            .subscribeOn(Schedulers.io())
            .observeOn(observeOnScheduler)
            .subscribeBy(
                onSuccess = {
                    view.hideProgress()
                    view.showComments(it)
                },
                onError = {
                    view.hideProgress()
                    view.showLoadingError()
                }
            )
    }

    override fun stop() {
        disposables.clear()
    }

}