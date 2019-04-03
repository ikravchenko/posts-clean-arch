package com.corewillsoft.posts.app.feature.posts

import android.app.ProgressDialog
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
import com.corewillsoft.posts.domain.post.repository.FavoriteRepository
import com.corewillsoft.posts.domain.post.repository.PostRepository
import com.corewillsoft.posts.domain.user.repository.UserRepository
import com.corewillsoft.posts.local.FavoriteRepositoryImpl
import com.corewillsoft.posts.local.UserRepositoryImpl
import com.corewillsoft.posts.presenter.login.UserInteractor
import com.corewillsoft.posts.presenter.login.UserInteractorImpl
import com.corewillsoft.posts.presenter.post.*
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

class PostsActivity : AppCompatActivity(), PostsView {

    @Inject
    lateinit var presenter: PostsPresenter

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

        postsAdapter = PostsAdapter(object : PostFavoriteListener {
            override fun onFavoriteToggled(id: Int, favorite: Boolean) {
                presenter.onPostIsFavoriteToggled(id, favorite)
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
        Snackbar.make(container, R.string.posts_loading_error_message, Snackbar.LENGTH_LONG).show()
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        })
        finish()
    }

    override fun navigateToComments(postId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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