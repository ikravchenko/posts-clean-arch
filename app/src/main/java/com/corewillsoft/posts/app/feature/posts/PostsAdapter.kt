package com.corewillsoft.posts.app.feature.posts

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.corewillsoft.posts.R
import com.corewillsoft.posts.presenter.post.PresentationPost

class PostsAdapter : RecyclerView.Adapter<PostViewHolder>() {

    var items: List<PresentationPost> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): PostViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_post,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = items.count()

    override fun onBindViewHolder(viewHolder: PostViewHolder, position: Int) {
        viewHolder.bind(items[position])
    }

}