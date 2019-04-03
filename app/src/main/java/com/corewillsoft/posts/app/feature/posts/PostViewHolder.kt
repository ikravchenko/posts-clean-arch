package com.corewillsoft.posts.app.feature.posts

import android.support.v7.widget.RecyclerView
import android.view.View
import com.corewillsoft.posts.presenter.post.PresentationPost
import kotlinx.android.synthetic.main.list_item_post.view.*

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(post: PresentationPost) {
        itemView.title.text = post.title
        itemView.subtitle.text = post.body
        itemView.favoriteButton.alpha = if (post.favorite) 1f else 0.1f
    }
}