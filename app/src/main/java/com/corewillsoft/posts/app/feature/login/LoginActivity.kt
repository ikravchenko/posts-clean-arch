package com.corewillsoft.posts.app.feature.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.corewillsoft.posts.R
import com.corewillsoft.posts.app.di.ContextModule
import com.corewillsoft.posts.app.feature.posts.PostsActivity
import com.corewillsoft.posts.domain.user.repository.UserRepository
import com.corewillsoft.posts.local.UserRepositoryImpl
import com.corewillsoft.posts.presenter.login.*
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.acticvity_login.*
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.SOURCE)
annotation class LoginScope

@LoginScope
@Component(modules = [LoginModule::class])
interface LoginComponent {
    fun inject(activity: LoginActivity)
}

@Module(includes = [ContextModule::class])
class LoginModule(private val view: LoginView) {
    @Provides
    fun provideView(): LoginView = view

    @Provides
    fun providePresenter(impl: LoginPresenterImpl): LoginPresenter = impl

    @Provides
    fun provideInteractor(impl: UserInteractorImpl): UserInteractor = impl

    @Provides
    fun provideUserRepository(impl: UserRepositoryImpl): UserRepository = impl

    @Provides
    @Named(UserRepositoryImpl.NAME)
    fun userPreferences(context: Context): SharedPreferences = context.getSharedPreferences(UserRepositoryImpl.NAME, Context.MODE_PRIVATE)
}

class LoginActivity : AppCompatActivity(), LoginView {

    @Inject
    lateinit var presenter: LoginPresenter

    override var loginEnabled: Boolean
        get() = loginButton.isEnabled
        set(value) {
            loginButton.isEnabled = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acticvity_login)
        DaggerLoginComponent.builder()
            .contextModule(ContextModule(application))
            .loginModule(LoginModule(this))
            .build()
            .inject(this)

        loginButton.setOnClickListener { presenter.onLoginClicked(userIdEditText.text.toString()) }
        userIdEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                presenter.onUserInputChanged(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

        })
        userIdEditText.setOnEditorActionListener { view: TextView, actionId: Int, _: KeyEvent? ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEND -> {
                    presenter.onLoginClicked(userIdEditText.text.toString())
                    true
                }
                else -> false
            }
        }
    }

    override fun navigateToPosts() {
        startActivity(Intent(this, PostsActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        })
        finish()
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onPause() {
        presenter.stop()
        super.onPause()
    }
}
