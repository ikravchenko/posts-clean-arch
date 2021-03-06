package com.corewillsoft.posts.app.feature.posts.list

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.corewillsoft.posts.R
import com.corewillsoft.posts.app.di.*
import com.corewillsoft.posts.app.feature.login.LoginActivity
import com.corewillsoft.posts.app.feature.posts.mapper.ParcelablePresentationPostMapper
import com.corewillsoft.posts.app.feature.posts.detail.PostDetailActivity
import com.corewillsoft.posts.domain.post.repository.FavoriteRepository
import com.corewillsoft.posts.domain.post.repository.PostRepository
import com.corewillsoft.posts.domain.user.repository.UserRepository
import com.corewillsoft.posts.local.FavoriteRepositoryImpl
import com.corewillsoft.posts.local.UserRepositoryImpl
import com.corewillsoft.posts.presenter.login.interactor.UserInteractor
import com.corewillsoft.posts.presenter.login.interactor.UserInteractorImpl
import com.corewillsoft.posts.presenter.post.list.interactor.PostInteractor
import com.corewillsoft.posts.presenter.post.list.interactor.PostInteractorImpl
import com.corewillsoft.posts.presenter.post.list.presenter.PostsPresenter
import com.corewillsoft.posts.presenter.post.list.presenter.PostsPresenterImpl
import com.corewillsoft.posts.presenter.post.list.view.PostsView
import com.corewillsoft.posts.presenter.post.model.PresentationPost
import com.corewillsoft.posts.remote.post.repository.PostRepositoryImpl
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.activity_posts.*
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.SOURCE)
annotation class PostsScope

@PostsScope
@Component(modules = [PostsModule::class], dependencies = [ServiceApiComponent::class])
interface PostsComponent {

    fun inject(activity: PostsActivity)
}

@Module(includes = [ContextModule::class, RxModule::class])
class PostsModule(private val view: PostsView) {
    @Provides
    fun provideView(): PostsView = view

    @Provides
    fun providePresenter(impl: PostsPresenterImpl): PostsPresenter = impl

    @Provides
    fun providePostInteractor(impl: PostInteractorImpl): PostInteractor = impl

    @Provides
    fun providePostRepository(impl: PostRepositoryImpl): PostRepository = impl

    @Provides
    fun provideFavoriteRepository(impl: FavoriteRepositoryImpl): FavoriteRepository = impl

    @Provides
    @Named(FavoriteRepositoryImpl.NAME)
    fun favoritePreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(FavoriteRepositoryImpl.NAME, Context.MODE_PRIVATE)

    @Provides
    fun provideUserInteractor(impl: UserInteractorImpl): UserInteractor = impl

    @Provides
    fun provideUserRepository(impl: UserRepositoryImpl): UserRepository = impl

    @Provides
    @Named(UserRepositoryImpl.NAME)
    fun userPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(UserRepositoryImpl.NAME, Context.MODE_PRIVATE)
}

/**
 * Represents all posts related to the current logged in user
 *
 * @see PostDetailActivity
 */
class PostsActivity : AppCompatActivity(), PostsView {

    @Inject
    lateinit var presenter: PostsPresenter

    @Inject
    lateinit var postMapper: ParcelablePresentationPostMapper

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_all -> {
                presenter.onAllClicked()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorites -> {
                presenter.onFavoriteClicked()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private lateinit var postsAdapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        DaggerPostsComponent.builder()
            .serviceApiComponent(DaggerServiceApiComponent.create())
            .contextModule(ContextModule(applicationContext))
            .postsModule(PostsModule(this))
            .build()
            .inject(this)

        initNavigationView(savedInstanceState)
        initRecyclerVIew()
    }

    private fun initNavigationView(savedInstanceState: Bundle?) {
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        if (savedInstanceState == null) {
            navigation.selectedItemId = R.id.navigation_all
        }
    }

    private fun initRecyclerVIew() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        postsRecyclerView.layoutManager = linearLayoutManager

        postsAdapter = PostsAdapter(object :
            PostInteractionListener {
            override fun onFavoriteToggled(id: Int, favorite: Boolean) {
                presenter.onPostIsFavoriteToggled(id, favorite)
            }

            override fun onPostClicked(post: PresentationPost) {
                presenter.onPostClicked(post)
            }
        })
        postsRecyclerView.adapter = postsAdapter
        postsRecyclerView.addItemDecoration(
            DividerItemDecoration(this, linearLayoutManager.orientation)
        )
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        navigation.selectedItemId = navigation.selectedItemId
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onPause() {
        presenter.stop()
        super.onPause()
    }

    override fun showPosts(posts: List<PresentationPost>) {
        postsAdapter.items = posts
    }

    override fun showLoadPostsError() {
        Snackbar.make(container, R.string.loading_error_message, Snackbar.LENGTH_LONG).show()
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun navigateToDetails(post: PresentationPost) {
        startActivity(Intent(this, PostDetailActivity::class.java).apply {
            putExtra(PostDetailActivity.POST_KEY, postMapper.to(post))
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.logoutAction) {
            presenter.onLogoutClicked()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}