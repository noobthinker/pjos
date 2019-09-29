package com.pj109.xkorey.pjos.ui.tag

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pj109.xkorey.pjos.databinding.ItemFilterChipBinding
import com.pj109.xkorey.pjos.util.executeAfter

class FilterChipAdapter(filterList:List<EventFilter>): RecyclerView.Adapter<TagChipViewHolder>(){

//    var filters: MutableList<EventFilter> = arrayListOf()

    var filters= filterList

    override fun getItemCount() = filters.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagChipViewHolder {
        return TagChipViewHolder(
            ItemFilterChipBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TagChipViewHolder, position: Int) {
        holder.bind(filters[position])
    }
}

class TagChipViewHolder(private val binding: ItemFilterChipBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: EventFilter) {
        binding.executeAfter {
            eventFilter = item
        }
    }
}
