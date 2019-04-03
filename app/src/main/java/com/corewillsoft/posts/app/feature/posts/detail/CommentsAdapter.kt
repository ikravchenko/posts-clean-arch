package com.corewillsoft.posts.app.feature.posts.detail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.corewillsoft.posts.R
import com.corewillsoft.posts.presenter.post.model.PresentationComment
import kotlinx.android.synthetic.main.list_item_comment.view.*

/**
 * Represents [PresentationComment]s
 */
class CommentsAdapter : RecyclerView.Adapter<CommentViewHolder>() {

    var items: List<PresentationComment> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CommentViewHolder {
        return CommentViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_comment,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = items.count()

    override fun onBindViewHolder(viewHolder: CommentViewHolder, position: Int) {
        viewHolder.bind(items[position])
    }

}

class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(comment: PresentationComment) {
        itemView.email.text = comment.email
        itemView.title.text = comment.name
        itemView.subtitle.text = comment.body
    }
}