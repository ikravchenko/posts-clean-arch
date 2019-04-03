package com.corewillsoft.posts.presenter.login

import com.corewillsoft.posts.domain.user.usecase.GetCurrentUserIdUseCase
import com.corewillsoft.posts.domain.user.usecase.LoginUseCase
import com.corewillsoft.posts.domain.user.usecase.LogoutUseCase
import io.reactivex.Completable
import javax.inject.Inject

interface UserInteractor {

    val currentUserId: Int?

    fun login(userId: Int): Completable

    fun logout(): Completable
}

class UserInteractorImpl @Inject constructor(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase
) : UserInteractor {

    override val currentUserId: Int?
        get() = getCurrentUserIdUseCase.execute()

    override fun login(userId: Int) = loginUseCase.execute(LoginUseCase.InParams(userId))

    override fun logout() = logoutUseCase.execute()
}