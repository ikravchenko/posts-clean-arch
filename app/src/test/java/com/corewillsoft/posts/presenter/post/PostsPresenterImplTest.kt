package com.corewillsoft.posts.presenter.post

import com.corewillsoft.posts.presenter.login.UserInteractor
import com.corewillsoft.posts.presenter.post.list.PostInteractor
import com.corewillsoft.posts.presenter.post.list.PostsPresenterImpl
import com.corewillsoft.posts.presenter.post.list.PostsView
import com.corewillsoft.posts.presenter.post.model.PresentationPost
import com.nhaarman.mockitokotlin2.*
import io.kotlintest.specs.WordSpec
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers

class PostsPresenterImplTest : WordSpec() {

    init {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }

        val view = mock<PostsView>()
        val postInteractor = mock<PostInteractor>()
        val userInteractor = mock<UserInteractor>()
        val presenter = PostsPresenterImpl(
            view = view,
            userInteractor = userInteractor,
            postInteractor = postInteractor,
            observeOnScheduler = Schedulers.trampoline()
        )

        "onAllClicked" should {

            "show progress and items on successful load" {
                val posts = listOf<PresentationPost>(mock())
                whenever(postInteractor.allPosts).thenReturn(Single.just(posts))

                presenter.onAllClicked()

                inOrder(view) {
                    verify(view).showProgress()
                    verify(view).hideProgress()
                    verify(view).showPosts(posts)
                }
            }

            "show progress and error on loading failure" {
                whenever(postInteractor.allPosts).thenReturn(Single.error(Exception("something went wrong")))

                presenter.onAllClicked()

                inOrder(view) {
                    verify(view).showProgress()
                    verify(view).hideProgress()
                    verify(view).showLoadPostsError()
                }
            }
        }

        "onFavoriteClicked" should {

            "show progress and items on successful load" {
                val posts = listOf<PresentationPost>(mock())
                whenever(postInteractor.favoritePosts).thenReturn(Single.just(posts))

                presenter.onFavoriteClicked()

                inOrder(view) {
                    verify(view).showProgress()
                    verify(view).hideProgress()
                    verify(view).showPosts(posts)
                }
            }

            "show progress and error on loading failure" {
                whenever(postInteractor.favoritePosts).thenReturn(Single.error(Exception("something went wrong")))

                presenter.onFavoriteClicked()

                inOrder(view) {
                    verify(view).showProgress()
                    verify(view).hideProgress()
                    verify(view).showLoadPostsError()
                }
            }

            "onPostIsFavoriteToggled" should {

                "toggle a post favorite status and refresh view" {
                    val posts = listOf<PresentationPost>(mock())
                    whenever(postInteractor.allPosts).thenReturn(Single.just(posts))
                    whenever(postInteractor.togglePostIsFavorite(any(), any())).thenReturn(Completable.complete())

                    presenter.onPostIsFavoriteToggled(1, true)

                    inOrder(view) {
                        verify(view).showProgress()
                        verify(view).hideProgress()
                        verify(view).showPosts(posts)
                    }

                    verify(postInteractor).togglePostIsFavorite(1, true)
                }
            }

            "onLogoutClicked" should {

                "navigate back to login" {
                    whenever(userInteractor.logout()).thenReturn(Completable.complete())

                    presenter.onLogoutClicked()

                    verify(view).navigateToLogin()
                    verify(userInteractor).logout()
                }
            }
        }
    }
}