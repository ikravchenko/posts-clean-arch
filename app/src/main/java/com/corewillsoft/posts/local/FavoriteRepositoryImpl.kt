package com.corewillsoft.posts.local

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.system.Os.remove
import com.corewillsoft.posts.domain.post.repository.FavoriteRepository
import io.reactivex.Single
import io.reactivex.internal.util.BackpressureHelper.add
import javax.inject.Inject
import javax.inject.Named

class FavoriteRepositoryImpl @Inject constructor(@Named(NAME) private val preferences: SharedPreferences) :
    FavoriteRepository {

    override val favoritePostIds: Set<Int>
        get() = preferences.getStringSet(IDS_KEY, emptySet<String>())!!.map { Integer.valueOf(it) }.toSet()

    @SuppressLint("CheckResult")
    override fun addPostId(id: Int) {
        val modified = favoritePostIds.toMutableSet().apply {
            add(id)
        }.map(Int::toString).toMutableSet()
        preferences.edit().putStringSet(IDS_KEY, modified).apply()
    }


    override fun removePostId(id: Int) {
        val modified = favoritePostIds.toMutableSet().apply {
            remove(id)
        }.map(Int::toString).toMutableSet()
        preferences.edit().putStringSet(IDS_KEY, modified).apply()
    }

    override fun clear() {
        preferences.edit().clear().apply()
    }


    companion object {
        const val NAME = "FAVORITES"
        private const val IDS_KEY = "IDS"
    }
}