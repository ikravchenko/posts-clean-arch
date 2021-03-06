package com.corewillsoft.posts.presenter.login.presenter

import com.corewillsoft.posts.presenter.PresenterLifecycle
import com.corewillsoft.posts.presenter.login.view.LoginView
import com.corewillsoft.posts.presenter.login.interactor.UserInteractor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

/**
 * Presents login view and logic
 *
 * @see LoginView
 */
interface LoginPresenter : PresenterLifecycle {

    fun onLoginClicked(userId: String)

    fun onUserInputChanged(userId: String)
}

class LoginPresenterImpl @Inject constructor(
    private val interactor: UserInteractor,
    private val view: LoginView
) : LoginPresenter {

    private val disposables: CompositeDisposable by lazy { CompositeDisposable() }

    override fun start() {
        interactor.currentUserId?.let {
            view.navigateToPosts()
            return
        }
        view.loginEnabled = false
    }

    override fun stop() {
        disposables.clear()
    }

    override fun onLoginClicked(userId: String) {
        userId.toIntOrNull()?.let { id ->
            disposables += interactor.login(id)
                .subscribeBy(onComplete = {
                    view.navigateToPosts()
                }, onError = {
                    //do nothing
                })
        }
    }

    override fun onUserInputChanged(userId: String) {
        view.loginEnabled = userId.toIntOrNull() != null
    }
}