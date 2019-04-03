package com.corewillsoft.posts.app.feature.posts.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.corewillsoft.posts.R
import com.corewillsoft.posts.presenter.post.model.PresentationPost
import kotlinx.android.synthetic.main.list_item_post.view.*

interface PostInteractionListener {

    fun onFavoriteToggled(id: Int, favorite: Boolean)

    fun onPostClicked(post: PresentationPost)
}

class PostsAdapter(private val postInteractionListener: PostInteractionListener) : RecyclerView.Adapter<PostViewHolder>() {

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
        viewHolder.bind(items[position], postInteractionListener)
    }

}

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(post: PresentationPost, postInteractionListener: PostInteractionListener) {
        itemView.title.text = post.title
        itemView.subtitle.text = post.body
        itemView.favoriteView.setImageResource(if (post.favorite) android.R.drawable.star_on else android.R.drawable.star_off)
        itemView.favoriteView.setOnClickListener { postInteractionListener.onFavoriteToggled(post.id, post.favorite) }
        itemView.setOnClickListener {postInteractionListener.onPostClicked(post)}
    }
}