package com.pj109.xkorey.pjos.ui.tag

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pj109.xkorey.pjos.R
import com.pj109.xkorey.pjos.databinding.ItemEventFilterBinding
import com.pj109.xkorey.share.util.exceptionInDebug
import com.pj109.xkorey.share.util.inflate

class TagAdapter(val viewModel: TagViewModel): ListAdapter<Any, RecyclerView.ViewHolder>(EventFilterDiff) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADING -> createHeadingViewHolder(parent)
            VIEW_TYPE_FILTER -> createFilterViewHolder(parent)
            else -> throw IllegalArgumentException("Unknown item type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeadingViewHolder -> holder.bind(getItem(position) as EventFilter.EventFilterCategory)
            is FilterViewHolder -> holder.bind(getItem(position) as EventFilter)
        }
    }

    override fun submitList(list: MutableList<Any>?) {
        exceptionInDebug(
            RuntimeException("call `submitEventFilterList()` instead to add category headings.")
        )
        super.submitList(list)
    }

    fun submitEventFilterList(list: List<EventFilter>?) {
        super.submitList(insertCategoryHeadings(list))
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is EventFilter.EventFilterCategory -> VIEW_TYPE_HEADING
            is EventFilter -> VIEW_TYPE_FILTER
            else -> throw IllegalArgumentException("Unknown item type")
        }
    }

    fun getSpanSize(position: Int): Int {
        return if (getItem(position) is EventFilter.TagFilter) 1 else 2
    }


    private fun createHeadingViewHolder(parent: ViewGroup): HeadingViewHolder {
        return HeadingViewHolder(parent.inflate(VIEW_TYPE_HEADING, false))
    }

    private fun createFilterViewHolder(parent: ViewGroup): FilterViewHolder {
        val binding = ItemEventFilterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        ).apply {
            viewModel = this@TagAdapter.viewModel
        }
        return FilterViewHolder(binding)
    }


    /** ViewHolder for category heading items. */
    class HeadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView = itemView as TextView

        internal fun bind(item: EventFilter.EventFilterCategory) {
            textView.setText(item.resId)
        }
    }

    /** ViewHolder for [TagFilter] items. */
    class FilterViewHolder(private val binding: ItemEventFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        internal fun bind(item: EventFilter) {
            binding.eventFilter = item
            binding.executePendingBindings()
        }
    }


    companion object {
        private const val VIEW_TYPE_HEADING = R.layout.item_filter_heading
        private const val VIEW_TYPE_FILTER = R.layout.item_event_filter

        /**
         * Inserts category headings in a list of [EventFilter]s to make a heterogeneous list.
         * Assumes the items are already sorted by the value of [EventFilter.getFilterCategory],
         * with items belonging to [NONE] first.
         */
        private fun insertCategoryHeadings(list: List<EventFilter>?): List<Any> {
            val newList = mutableListOf<Any>()
            var previousCategory: EventFilter.EventFilterCategory = EventFilter.EventFilterCategory.NONE
            list?.forEach {
                val category = it.getFilterCategory()
                if (category != previousCategory && category != EventFilter.EventFilterCategory.NONE) {
                    newList.add(category)
                }
                newList.add(it)
                previousCategory = category
            }
            return newList
        }
    }

}


internal object EventFilterDiff : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any) = oldItem == newItem

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        // This method is only called if areItemsTheSame() returns true. For anything other than
        // TagFilter items, that check suffices for this one as well.
        return (oldItem as? EventFilter.TagFilter)?.isUiContentEqual(newItem as EventFilter.TagFilter) ?: true
    }
}

internal class ScheduleFilterSpanSizeLookup(private val adapter: TagAdapter) :
    GridLayoutManager.SpanSizeLookup() {

    override fun getSpanSize(position: Int) = adapter.getSpanSize(position)
}