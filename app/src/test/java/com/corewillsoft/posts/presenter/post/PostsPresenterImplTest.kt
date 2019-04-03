package com.corewillsoft.posts.presenter.post

import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.kotlintest.specs.WordSpec
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers

class PostsPresenterImplTest : WordSpec() {

    init {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }

        val view = mock<PostsView>()
        val interactor = mock<PostInteractor>()
        val presenter = PostsPresenterImpl(view = view, interactor = interactor, observeOnScheduler = Schedulers.trampoline())

        "onAllClicked" should {

            "show progress and items on successful load" {
                val posts = listOf<PresentationPost>(mock())
                whenever(interactor.allPosts).thenReturn(Single.just(posts))

                presenter.onAllClicked()

                inOrder(view) {
                    verify(view).showProgress()
                    verify(view).hideProgress()
                    verify(view).showPosts(posts)
                }
            }

            "show progress and error on loading failure" {
                whenever(interactor.allPosts).thenReturn(Single.error(Exception("something went wrong")))

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
                whenever(interactor.favoritePosts).thenReturn(Single.just(posts))

                presenter.onFavoriteClicked()

                inOrder(view) {
                    verify(view).showProgress()
                    verify(view).hideProgress()
                    verify(view).showPosts(posts)
                }
            }

            "show progress and error on loading failure" {
                whenever(interactor.favoritePosts).thenReturn(Single.error(Exception("something went wrong")))

                presenter.onFavoriteClicked()

                inOrder(view) {
                    verify(view).showProgress()
                    verify(view).hideProgress()
                    verify(view).showLoadPostsError()
                }
            }
        }
    }
}