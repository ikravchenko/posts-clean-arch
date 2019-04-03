package com.corewillsoft.posts.local

import android.content.SharedPreferences
import com.corewillsoft.posts.domain.user.repository.UserRepository
import javax.inject.Inject
import javax.inject.Named

class UserRepositoryImpl @Inject constructor(@Named(NAME) private val preferences: SharedPreferences) : UserRepository {
    override var id: Int?
        get() = if (preferences.contains(USER_ID_KEY)) preferences.getInt(USER_ID_KEY, 0) else null
        set(value) {
            value?.let {
            preferences.edit().putInt(USER_ID_KEY, it).apply()
            } ?: preferences.edit().remove(USER_ID_KEY).apply()
        }

    companion object {
        const val NAME = "USER"
        private const val USER_ID_KEY = "USER_ID"
    }
}