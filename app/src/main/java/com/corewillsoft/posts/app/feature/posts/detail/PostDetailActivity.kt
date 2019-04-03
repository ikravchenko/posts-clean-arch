package com.corewillsoft.posts.app.feature.posts.detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.Snackbar
import android.support.v4.app.NavUtils
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.corewillsoft.posts.R
import com.corewillsoft.posts.app.di.ContextModule
import com.corewillsoft.posts.app.di.DaggerServiceApiComponent
import com.corewillsoft.posts.app.di.RxModule
import com.corewillsoft.posts.app.di.ServiceApiComponent
import com.corewillsoft.posts.domain.BidirectionalMapper
import com.corewillsoft.posts.domain.comment.repository.CommentRepository
import com.corewillsoft.posts.presenter.post.detail.*
import com.corewillsoft.posts.presenter.post.model.PresentationComment
import com.corewillsoft.posts.presenter.post.model.PresentationPost
import com.corewillsoft.posts.remote.comment.repository.CommentRepositoryImpl
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_post_comments.*
import kotlinx.android.synthetic.main.activity_posts.*
import kotlinx.android.synthetic.main.list_item_comment.view.*
import javax.inject.Inject
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.SOURCE)
annotation class PostDetailScope

@PostDetailScope
@Component(modules = [PostDetailModule::class], dependencies = [ServiceApiComponent::class])
interface PostDetailComponent {

    fun inject(activity: PostDetailActivity)
}

@Module(includes = [ContextModule::class, RxModule::class])
class PostDetailModule(
    private val view: PostDetailView,
    private val state: PostDetailPresenterImpl.PresenterState
) {

    @Provides
    fun providePresenterState(): PostDetailPresenterImpl.PresenterState = state

    @Provides
    fun provideView(): PostDetailView = view

    @Provides
    fun providePresenter(impl: PostDetailPresenterImpl): PostDetailPresenter = impl

    @Provides
    fun provideInteractor(impl: CommentInteractorImpl): CommentInteractor = impl

    @Provides
    fun provideCommentsRepository(impl: CommentRepositoryImpl): CommentRepository = impl
}


@Parcelize
class ParcelablePost(
    val id: Int,
    val title: String,
    val body: String,
    val favorite: Boolean
) : Parcelable

class ParcelablePresentationPostMapper @Inject constructor() : BidirectionalMapper<ParcelablePost, PresentationPost> {

    override fun from(from: ParcelablePost) = with(from) {
        PresentationPost(
            id = id,
            favorite = favorite,
            title = title,
            body = body
        )
    }

    override fun to(from: PresentationPost) = with(from) {
        ParcelablePost(
            id = id,
            favorite = favorite,
            title = title,
            body = body
        )
    }

}

class PostDetailActivity : AppCompatActivity(), PostDetailView {

    companion object {
        const val POST_KEY = "POST_KEY"
    }

    @Inject
    lateinit var presenter: PostDetailPresenter

    private lateinit var commentsAdapter: CommentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_comments)

        if (!intent.hasExtra(POST_KEY)) {
            finish()
            return
        }

        val post = ParcelablePresentationPostMapper().from(intent.getParcelableExtra(POST_KEY))
        DaggerPostDetailComponent.builder()
            .serviceApiComponent(DaggerServiceApiComponent.create())
            .contextModule(ContextModule(applicationContext))
            .postDetailModule(PostDetailModule(this, PostDetailPresenterImpl.PresenterState(post)))
            .build()
            .inject(this)

        initRecyclerVIew()
    }

    private fun initRecyclerVIew() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        commentsRecyclerView.layoutManager = linearLayoutManager

        commentsAdapter = CommentsAdapter()
        commentsRecyclerView.adapter = commentsAdapter
        commentsRecyclerView.addItemDecoration(
            DividerItemDecoration(this, linearLayoutManager.orientation)
        )
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onPause() {
        presenter.stop()
        super.onPause()
    }

    override fun showPost(post: PresentationPost) {
        postTitle.text = post.title
        postSubtitle.text = post.body
        postFavoriteView.visibility = if (post.favorite) View.VISIBLE else View.GONE
    }

    override fun showComments(comments: List<PresentationComment>) {
        commentsAdapter.items = comments
    }

    override fun showLoadingError() {
        Snackbar.make(container, R.string.loading_error_message, Snackbar.LENGTH_LONG).show()
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }
}

class CommentsAdapter : RecyclerView.Adapter<CommentViewHolder>() {

    var items: List<PresentationComment> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CommentViewHolder {
        return CommentViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_comment,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = items.count()

    override fun onBindViewHolder(viewHolder: CommentViewHolder, position: Int) {
        viewHolder.bind(items[position])
    }

}

class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(comment: PresentationComment) {
        itemView.email.text = comment.email
        itemView.title.text = comment.name
        itemView.subtitle.text = comment.body
    }
}

