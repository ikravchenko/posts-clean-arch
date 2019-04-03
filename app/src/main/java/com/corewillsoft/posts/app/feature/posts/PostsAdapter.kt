package com.corewillsoft.posts.app.feature.posts

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.corewillsoft.posts.R
import com.corewillsoft.posts.presenter.post.PresentationPost
import kotlinx.android.synthetic.main.list_item_post.view.*

interface PostFavoriteListener {

    fun onFavoriteToggled(id: Int, favorite: Boolean)
}

class PostsAdapter(private val postFavoriteListener: PostFavoriteListener) : RecyclerView.Adapter<PostViewHolder>() {

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
        viewHolder.bind(items[position], postFavoriteListener)
    }

}

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(post: PresentationPost, postFavoriteListener: PostFavoriteListener) {
        itemView.title.text = post.title
        itemView.subtitle.text = post.body
        itemView.favoriteView.visibility = if (post.favorite) View.VISIBLE else View.GONE
        itemView.setOnClickListener { postFavoriteListener.onFavoriteToggled(post.id, post.favorite) }
    }
}