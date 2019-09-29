package com.pj109.xkorey.pjos.ui.image.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pj109.xkorey.model.room.Media
import com.pj109.xkorey.pjos.R
import com.pj109.xkorey.pjos.databinding.ItemImageViewBinding
import java.util.*

class RecyclerViewItemsAdapter : RecyclerView.Adapter<RecyclerViewItemsViewHolder>() {

    var images: List<Media> = Collections.emptyList()
        set(value) {
            field = value
            differ.submitList(buildMergedList(list = value))
        }

    private val differ = AsyncListDiffer<Any>(this, DiffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewItemsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_image_view-> RecyclerViewItemsViewHolder.ImageItemViewHolder(
                ItemImageViewBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )
            else -> throw IllegalStateException("Unknown viewType $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (differ.currentList[position]) {
            is Media -> R.layout.item_image_view
            else -> throw IllegalStateException("Unknown view type at position $position")
        }
    }


    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: RecyclerViewItemsViewHolder, position: Int) {
        when(holder){
            is RecyclerViewItemsViewHolder.ImageItemViewHolder -> holder.binding.apply {
                imageItem=differ.currentList[position] as Media
                setLifecycleOwner(lifecycleOwner)
                executePendingBindings()
            }
        }
    }




    private fun buildMergedList(
        list: List<Media> = images
    ): List<Any> {
        val merged = mutableListOf<Any>()
        if (list.isNotEmpty()) {
            merged.addAll(list)
        }
        return merged
    }


}

object ImageItem

object ImageItemEventsHeaderItem

object DiffCallback : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return when {
            oldItem === ImageItem && newItem === ImageItem -> true
            oldItem === ImageItemEventsHeaderItem && newItem === ImageItemEventsHeaderItem -> true
            oldItem is Media && newItem is Media ->
                oldItem.id == newItem.id
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return when {
            oldItem is Media && newItem is Media -> oldItem.id == newItem.id
            else -> false
        }
    }
}

sealed class RecyclerViewItemsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
    class ImageItemViewHolder(val binding: ItemImageViewBinding):RecyclerViewItemsViewHolder(binding.root)
}

