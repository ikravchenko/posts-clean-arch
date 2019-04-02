package com.corewillsoft.posts.local

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.corewillsoft.posts.domain.post.repository.FavoriteRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class FavoriteRepositoryImpl @Inject constructor(@Named(NAME) private val preferences: SharedPreferences) :
    FavoriteRepository {

    override val favoritePostIds: Single<Set<Int>>
        get() = Single.just(preferences.getStringSet(IDS_KEY, emptySet<String>())!!.map { Integer.valueOf(it) }.toSet())

    @SuppressLint("CheckResult")
    override fun addPostId(id: Int) =
        favoritePostIds.doOnSuccess {
            val modified = it.toMutableSet().apply {
                add(id)
            }.map(Int::toString).toMutableSet()
            preferences.edit().putStringSet(IDS_KEY, modified).apply()
        }.ignoreElement()


    override fun removePostId(id: Int) =
        favoritePostIds.doOnSuccess {
            val modified = it.toMutableSet().apply {
                remove(id)
            }.map(Int::toString).toMutableSet()
            preferences.edit().putStringSet(IDS_KEY, modified).apply()
        }.ignoreElement()


    companion object {
        const val NAME = "FAVORITES"
        private const val IDS_KEY = "IDS"
    }
}