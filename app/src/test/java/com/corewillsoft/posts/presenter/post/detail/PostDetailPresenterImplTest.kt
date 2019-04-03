package com.corewillsoft.posts.presenter.post.detail

import com.corewillsoft.posts.presenter.post.model.PresentationComment
import com.corewillsoft.posts.presenter.post.model.PresentationPost
import com.nhaarman.mockitokotlin2.*
import io.kotlintest.mock.mock
import io.kotlintest.specs.WordSpec
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class PostDetailPresenterImplTest : WordSpec() {

    init {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        val post = PresentationPost(
            id = 1,
            body = "body",
            title = "title",
            favorite = true
        )
        val view = mock<PostDetailView>()
        val interactor = mock<CommentInteractor>()
        val presenter = PostDetailPresenterImpl(
            state = PostDetailPresenterImpl.PresenterState(post),
            view = view,
            interactor = interactor,
            observeOnScheduler = Schedulers.trampoline()
        )

        "start" should {

            "show post" {
                presenter.start()

                verify(view).showPost(post)
            }

            "show comments with progress if load succeeds" {
                val comments = listOf<PresentationComment>(com.nhaarman.mockitokotlin2.mock())
                whenever(interactor.getCommentsForPost(any())).thenReturn(Single.just(comments))

                presenter.start()

                inOrder(view) {
                    verify(view).showProgress()
                    verify(view).hideProgress()
                    verify(view).showComments(comments)
                }
            }

            "show error with progress if load fails" {
                whenever(interactor.getCommentsForPost(any())).thenReturn(Single.error(Exception("something went wrong")))

                presenter.start()

                inOrder(view) {
                    verify(view).showProgress()
                    verify(view).hideProgress()
                    verify(view).showLoadingError()
                }
            }
        }
    }
}