package com.corewillsoft.posts.app.feature.posts.mapper

import com.corewillsoft.posts.app.feature.posts.model.ParcelablePost
import com.corewillsoft.posts.common.BidirectionalMapper
import com.corewillsoft.posts.presenter.post.model.PresentationPost
import javax.inject.Inject

class ParcelablePresentationPostMapper @Inject constructor() :
    BidirectionalMapper<ParcelablePost, PresentationPost> {

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