package com.pj109.xkorey.pjos.ui.tag

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pj109.xkorey.model.room.Media
import com.pj109.xkorey.model.room.Tag
import com.pj109.xkorey.pjos.ui.login.SignInViewModelDelegate
import com.pj109.xkorey.pjos.ui.media.TransientUiState
import com.pj109.xkorey.pjos.util.randomColor
import com.pj109.xkorey.share.domain.config.TagMatcher
import com.pj109.xkorey.share.domain.prefs.LoadSelectedFiltersUseCase
import com.pj109.xkorey.share.domain.prefs.SaveSelectedFiltersUseCase
import com.pj109.xkorey.share.domain.room.ConfigQueryUseCase
import com.pj109.xkorey.share.domain.room.TagSaveUseCase
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class TagViewModel @Inject constructor(
    private val signInViewModelDelegate: SignInViewModelDelegate,
    private val configQueryUseCase: ConfigQueryUseCase,
    private val tagSaveUseCase: TagSaveUseCase,
    private val saveSelectedFiltersUseCase: SaveSelectedFiltersUseCase,
    private val loadSelectedFiltersUseCase: LoadSelectedFiltersUseCase
):ViewModel(),TagEventListener,SignInViewModelDelegate by signInViewModelDelegate {

    private val _hasAnyFilters = MutableLiveData<Boolean>()
    val hasAnyFilters: LiveData<Boolean>
        get() = _hasAnyFilters
//    private val _selectedFilters = MutableLiveData<List<EventFilter>>()
    val selectedFilters=ObservableField<List<EventFilter>>()
//        get() = _selectedFilters
    val _eventCount=MutableLiveData<Int>()
    val eventCount: LiveData<Int>
        get() = _eventCount
    val _tagFilters=MutableLiveData<List<EventFilter>>()
    val tagFilters:LiveData<List<EventFilter>>
        get() = _tagFilters
    private val tagMatcher = TagMatcher()
    private var cachedEventFilters = emptyList<EventFilter>()
    private var _transientUiStateVar = TransientUiState(false, false)
    private val _transientUiState = MutableLiveData<TransientUiState>()
    val transientUiState: LiveData<TransientUiState>
        get() = _transientUiState


    init {
        val tag1= Tag(1,1,"test","image",1, randomColor(), randomColor(), Date())
        val tag2= Tag(2,2,"fun","video",1, randomColor(), randomColor(), Date())

        cachedEventFilters=arrayListOf(EventFilter.TagFilter(tag1,false),
            EventFilter.TagFilter(tag2,false))

        _tagFilters.postValue(
            cachedEventFilters
        )

        updateFilterStateObservables()
    }

    override fun toggleFilter(filter: EventFilter, enabled: Boolean) {
        val changed = when (filter) {
            is EventFilter.MyEventsFilter -> tagMatcher.setShowPinnedEventsOnly(enabled)
            is EventFilter.TagFilter -> {
                if (enabled) {
                    tagMatcher.add(filter.tag)
                } else {
                    tagMatcher.remove(filter.tag)
                }
            }
        }
        if (changed) {
            filter.isChecked.set(enabled)
            saveSelectedFiltersUseCase(tagMatcher)
            updateFilterStateObservables()
        }
        Timber.i("toggle click")
    }

    override fun openEventDetail(id: Int) {
        Timber.i("open click")
    }

    override fun onStarClicked(userSession: Media) {
        Timber.i("media click")
    }




    override fun clearFilters(){
        if (tagMatcher.clearAll()) {
            tagFilters.value?.forEach { it.isChecked.set(false) }
            saveSelectedFiltersUseCase(tagMatcher)
            updateFilterStateObservables()
        }
    }

    private fun updateFilterStateObservables() {
        val hasAnyFilters = tagMatcher.hasAnyFilters()
        _hasAnyFilters.value = hasAnyFilters
//        _selectedFilters.value = cachedEventFilters.filter { it.isChecked.get() }
        selectedFilters.set(cachedEventFilters.filter { it.isChecked.get() })
//        _tagFilters.postValue(
//            cachedEventFilters
//        )
        setTransientUiState(_transientUiStateVar.copy(hasAnyFilters = hasAnyFilters))
    }

    private fun setTransientUiState(state: TransientUiState) {
        _transientUiStateVar = state
        _transientUiState.value = state
    }


}

interface TagEventListener : EventActions {
    /** Called from the UI to enable or disable a particular filter. */
    fun toggleFilter(filter: EventFilter, enabled: Boolean)

    /** Called from the UI to remove all filters. */
    fun clearFilters()
}

interface EventActions {
    fun openEventDetail(id: Int)
    fun onStarClicked(userSession: Media)
}

