package com.pj109.xkorey.pjos.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pj109.xkorey.model.room.Tag
import com.pj109.xkorey.pjos.databinding.MediaItemTagBinding


class TagAdapter : RecyclerView.Adapter<TagViewHolder>() {

    var tags = emptyList<Tag>()
    override fun getItemCount() = tags.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        return TagViewHolder(
            MediaItemTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bind(tags[position])
    }
}


class TagViewHolder(private val binding: MediaItemTagBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(tag: Tag) {
        binding.tag = tag
        binding.executePendingBindings()
    }
}
