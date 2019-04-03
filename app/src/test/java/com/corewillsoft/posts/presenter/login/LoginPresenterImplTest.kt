package com.corewillsoft.posts.presenter.login

import com.corewillsoft.posts.presenter.login.interactor.UserInteractor
import com.corewillsoft.posts.presenter.login.presenter.LoginPresenterImpl
import com.corewillsoft.posts.presenter.login.view.LoginView
import com.nhaarman.mockitokotlin2.*
import io.kotlintest.specs.WordSpec
import io.reactivex.Completable

class LoginPresenterImplTest : WordSpec() {

    init {

        val view = mock<LoginView>()
        val interactor = mock<UserInteractor>()
        val presenter =
            LoginPresenterImpl(interactor = interactor, view = view)

        "start" should {

            "navigate view to posts if user is available" {
                whenever(interactor.currentUserId).thenReturn(1)

                presenter.start()

                verify(view).navigateToPosts()
                verifyNoMoreInteractions(view)
            }

            "disables login if there is no user saved" {
                whenever(interactor.currentUserId).thenReturn(null)

                presenter.start()

                verify(view).loginEnabled = false
                verifyNoMoreInteractions(view)
            }
        }

        "onLoginClicked" should {

            "navigate view to posts if user is a number" {
                whenever(interactor.login(any())).thenReturn(Completable.complete())

                presenter.onLoginClicked("1")

                verify(view).navigateToPosts()
                verifyNoMoreInteractions(view)
            }

            "do nothing on view if user is not number" {
                whenever(interactor.login(any())).thenReturn(Completable.complete())

                presenter.onLoginClicked("s")

                verifyZeroInteractions(view)
            }
        }

        "onUserInputChanged" should {

            "activate login if input is a number" {
                presenter.onUserInputChanged("1")

                verify(view).loginEnabled = true
            }

            "deactivate login if input is not a number" {
                presenter.onUserInputChanged("q")

                verify(view).loginEnabled = false
            }

            "deactivate login if input is empty" {
                presenter.onUserInputChanged("")

                verify(view).loginEnabled = false
            }

        }
    }
}