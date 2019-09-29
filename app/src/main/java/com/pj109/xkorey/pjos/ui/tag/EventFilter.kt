package com.pj109.xkorey.pjos.ui.tag

import android.graphics.Color
import androidx.annotation.StringRes
import androidx.databinding.ObservableBoolean
import com.pj109.xkorey.model.room.Tag
import com.pj109.xkorey.pjos.R
import com.pj109.xkorey.pjos.util.hasSameValue

sealed class EventFilter(isChecked: Boolean) {

    enum class EventFilterCategory(@StringRes val resId: Int) {
        NONE(0),
        Image(R.string.title_image),
        Video(R.string.title_media)
    }

    val isChecked = ObservableBoolean(isChecked)

    /** Determines the category heading to show in the filters sheet. */
    abstract fun getFilterCategory(): EventFilterCategory

    /** Return the background color when filled. */
    abstract fun getColor(): Int

    /** Return a color to use when the filter is selected, or TRANSPARENT to use the default. */
    open fun getSelectedTextColor(): Int = Color.TRANSPARENT

    /** Return a string resource to display, or 0 to use the value of [getText]. */
    open fun getTextResId(): Int = 0

    /** Return a string to display when [getTextResId] returns 0. */
    open fun getText(): String = ""

    /** Return a short string resource to display, or 0 to use the value of [getShortText]. */
    open fun getShortTextResId(): Int = 0

    /** Return a short string string to display when [getShortTextResId] returns 0. */
    open fun getShortText(): String = ""

    /** Filter for user's starred and reserved events. */
    class MyEventsFilter(isChecked: Boolean) : EventFilter(isChecked) {

        override fun getFilterCategory(): EventFilterCategory = EventFilterCategory.NONE

        override fun getColor(): Int = Color.parseColor("#4768fd") // @color/indigo

        override fun getSelectedTextColor(): Int = Color.WHITE

        override fun getTextResId(): Int = R.string.starred_and_reserved

        override fun getShortTextResId(): Int = R.string.starred_and_reserved_short

        override fun equals(other: Any?): Boolean = other is MyEventsFilter
    }

    /** Filter for event tags. */
    class TagFilter(val tag: Tag, isChecked: Boolean) : EventFilter(isChecked) {

        override fun getFilterCategory(): EventFilterCategory {
            return when (tag.mediaType) {
                Tag.Image -> EventFilterCategory.Image
                Tag.Video -> EventFilterCategory.Video
                else -> throw IllegalArgumentException("unsupported tag type in filters")
            }
        }

        override fun getColor(): Int = tag.color!!

        override fun getSelectedTextColor(): Int = tag.fontColor ?: super.getSelectedTextColor()

        override fun getTextResId(): Int = 0
        override fun getShortTextResId(): Int = 0

        override fun getText(): String = tag.tagName!!
        override fun getShortText(): String = tag.tagName!!

        /** Only the tag is used for equality. */
        override fun equals(other: Any?) =
            this === other || (other is TagFilter && other.tag.isUiContentEqual(tag))

        /** Only the tag is used for equality. */
        override fun hashCode() = tag.hashCode()

        // for DiffCallback
        fun isUiContentEqual(other: TagFilter) =
            tag.isUiContentEqual(other.tag) && isChecked.hasSameValue(other.isChecked)
    }
}
