package com.corewillsoft.posts.app.feature.posts.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ParcelablePost(
    val id: Int,
    val title: String,
    val body: String,
    val favorite: Boolean
) : Parcelable