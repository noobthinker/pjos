package com.pj109.xkorey.share.domain.config

import com.google.gson.GsonBuilder
import com.pj109.xkorey.model.room.Tag
import com.pj109.xkorey.share.data.prefs.PreferenceStorage
import timber.log.Timber

class TagMatcher {

    fun save(preferenceStorage: PreferenceStorage) {
        val state = SavedFilterPreferences(showPinnedEventsOnly, selectedTags.toList())
        preferenceStorage.selectedFilters = gson.toJson(state)
    }

    fun load(preferenceStorage: PreferenceStorage) {
        val prefValue = preferenceStorage.selectedFilters
        if (prefValue != null) {
            val state = try {
                gson.fromJson(prefValue, SavedFilterPreferences::class.java)
            } catch (t: Throwable) {
                Timber.e(t, "Error reading filter preferences")
                return
            }
            showPinnedEventsOnly = state.showPinnedEventsOnly
            selectedTags.addAll(state.tagsAndCategories)
        }
    }

    @Synchronized
    fun matches(tag: Tag): Boolean {
        if (showPinnedEventsOnly) {
            return false
        }
        //todo: change here tag select
        var match = false
        return match
    }


    @Synchronized
    fun removeOrphanedTags(newTags: List<Tag>) {
        if (newTags.isEmpty()) {
            selectedTags.clear()
        } else {
            val valid = newTags.map { TagIdAndCategory(it.id!!, it.tagName!!) }
            selectedTags.removeAll(selectedTags.subtract(valid))
        }
    }

    @Synchronized
    operator fun contains(tag: Tag) = selectedTags.contains(TagIdAndCategory.fromTag(tag))

    @Synchronized
    fun hasAnyFilters(): Boolean {
        return showPinnedEventsOnly || selectedTags.isNotEmpty()
    }

    @Synchronized
    fun clearAll(): Boolean {
        val changed = hasAnyFilters()
        showPinnedEventsOnly = false
        selectedTags.clear()
        return changed
    }

    @Synchronized
    fun addAll(vararg tags: Tag): Boolean {
        var changed = false
        tags.forEach {
            changed = changed or add(it)
        }
        return changed
    }

    @Synchronized
    fun remove(tag: Tag) = selectedTags.remove(TagIdAndCategory.fromTag(tag))

    @Synchronized
    fun add(tag: Tag) = selectedTags.add(TagIdAndCategory.fromTag(tag))

    @Synchronized
    fun getShowPinnedEventsOnly() = showPinnedEventsOnly

    @Synchronized
    fun setShowPinnedEventsOnly(pinnedOnly: Boolean): Boolean {
        if (showPinnedEventsOnly != pinnedOnly) {
            showPinnedEventsOnly = pinnedOnly
            return true
        }
        return false
    }

    private var showPinnedEventsOnly = false

    private val selectedTags = HashSet<TagIdAndCategory>()

    private val gson = GsonBuilder().create()

    companion object {
        // Used to filter out Tag categories and define which order to display them.
        val FILTER_CATEGORIES = arrayOf(Tag.Image, Tag.Video)
    }
}

// Copy of Tag with only the id and category, for smaller serializing/deserializing.
data class TagIdAndCategory(
    val id: Int,
    val category: String
) {
    /** Only IDs are used for equality. */
    override fun equals(other: Any?): Boolean =
        this === other || (other is TagIdAndCategory && other.id == id)

    /** Only IDs are used for equality. */
    override fun hashCode(): Int = id.hashCode()

    companion object {
        fun fromTag(tag: Tag): TagIdAndCategory {
            return TagIdAndCategory(tag.id!!, tag.tagName!!)
        }
    }
}

data class SavedFilterPreferences(
    val showPinnedEventsOnly: Boolean,
    val tagsAndCategories: List<TagIdAndCategory>
)