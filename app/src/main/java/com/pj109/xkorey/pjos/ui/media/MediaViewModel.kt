package com.pj109.xkorey.pjos.ui.media

import android.graphics.drawable.Drawable
import android.view.View
import androidx.lifecycle.*
import com.pj109.xkorey.model.dto.MediaAndTags
import com.pj109.xkorey.model.enums.MediaType
import com.pj109.xkorey.model.enums.RoomEvent
import com.pj109.xkorey.model.enums.RoomType
import com.pj109.xkorey.model.room.Media
import com.pj109.xkorey.pjos.R
import com.pj109.xkorey.pjos.ui.common.EventAction
import com.pj109.xkorey.pjos.ui.login.SignInViewModelDelegate
import com.pj109.xkorey.share.data.NettyAllInOne
import com.pj109.xkorey.share.domain.http.RefreshDataUseCase
import com.pj109.xkorey.share.domain.room.MediaRoomOperationUseCase
import com.pj109.xkorey.share.domain.socket.NettyClientUseCase
import com.pj109.xkorey.share.domain.socket.RefreshViewModel
import com.pj109.xkorey.share.domain.socket.RoomEventService
import com.pj109.xkorey.share.result.Event
import com.pj109.xkorey.share.result.Result
import com.pj109.xkorey.share.result.succeeded
import com.pj109.xkorey.share.util.defaultReq
import com.pj109.xkorey.share.util.map
import timber.log.Timber
import javax.inject.Inject


class MediaViewModel  @Inject constructor(
    private val nettyClientUseCase: NettyClientUseCase,
    private val mediaUseCase: MediaRoomOperationUseCase,
    private val refreshDataUseCase: RefreshDataUseCase,
    signInViewModelDelegate: SignInViewModelDelegate
    ): RefreshViewModel(nettyClientUseCase),MediaEventListener,SignInViewModelDelegate by signInViewModelDelegate {


    private val roomEventService: RoomEventService

    private val _socketData = MutableLiveData<Result<NettyAllInOne.AllInone>>()
    val socketLiveData:LiveData<Result<NettyAllInOne.AllInone>>
        get() = _socketData
    private val _functionVisibility=MutableLiveData<Int>()
    val functionVisibility:LiveData<Int>
        get() = _functionVisibility
    val placeHolder=MutableLiveData<Drawable>()

    private val _mediaType = MutableLiveData<MediaType>()
    val mediaType:LiveData<MediaType>
        get()=_mediaType
    val swipeRefreshing: LiveData<Boolean>

    private val _selectTags = MutableLiveData<List<MediaAndTags>>()
    val selectTags:LiveData<List<MediaAndTags>>
        get()=_selectTags

    private val _isLoading=MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _profileContentDesc = MediatorLiveData<Int>().apply { value = R.string.sign_in }
    val profileContentDesc: LiveData<Int>
        get() = _profileContentDesc

    private val swipeRefreshResult = MutableLiveData<Result<Boolean>>()
    val refreshEnabled =MutableLiveData<Boolean>()

    private val _defaultTabs = MutableLiveData<List<String>>()
    val defaultTabs:LiveData<List<String>>
        get() = _defaultTabs

    private val _navigateToSignInDialogAction = MutableLiveData<Event<Unit>>()
    val navigateToSignInDialogAction: LiveData<Event<Unit>>
        get() = _navigateToSignInDialogAction
    private val _signOkAction = MutableLiveData<Event<Unit>>()
    val signOkAction:LiveData<Event<Unit>>
        get() = _signOkAction

    private val _navigateToSignOutDialogAction = MutableLiveData<Event<Unit>>()
    val navigateToSignOutDialogAction: LiveData<Event<Unit>>
        get() = _navigateToSignOutDialogAction

    private val _navigateToMediaAction = MutableLiveData<Event<Media>>()
    val navigateToMediaAction: LiveData<Event<Media>>
        get() = _navigateToMediaAction

    init {
        swipeRefreshing = swipeRefreshResult.map {
            false
        }

        if(isSignedIn()){
            _functionVisibility.postValue(View.VISIBLE)
        }else{
            _functionVisibility.postValue(View.GONE)
        }

        refreshEnabled.postValue(true)
        val _tabs = listOf(_mediaType.value, R.string.title_settings)
        _defaultTabs.postValue(_tabs as List<String>?)
        roomEventService=RoomEventService(this)
        socketData.addSource(socketLiveData,{
            when{
                it.succeeded->{
                    roomEventService.onMessageReceived((it as Result.Success).data)
                }

            }
        })

    }

    fun bind(owner:LifecycleOwner){
        socketData.observe(owner, Observer {
            roomEventService.onMessageReceived((it as Result.Success).data)
        })
    }


    fun onSwipeRefresh() {
        val req=defaultReq("refresh")
         refreshDataUseCase(req, swipeRefreshResult)

    }

    override fun openMediaDetail(id: Int) {
        val _media = Media.onlyId(id)
        val mediaList =mediaUseCase.invoke(Pair(_media,RoomType.Query))
        mediaList.map {
            val t=(it as Result.Success)?.data
            _navigateToMediaAction.postValue(Event(t[0]))
        }
    }

    override fun onStarClicked(media: MediaAndTags) {
        Timber.i("media tart event:id ${media.media.id}")
    }


    override fun refresh(event: RoomEvent) {
        Timber.i("socket event ${event.name}")
    }

    fun onProfileClicked() {
        if (isSignedIn()) {
            _navigateToSignOutDialogAction.value = Event(Unit)
        } else {
            _navigateToSignInDialogAction.value = Event(Unit)
        }
    }

    fun changeLoading(loading:Boolean){
        _isLoading.postValue(loading)
    }

}


interface MediaEventListener : EventAction {

}

data class TransientUiState(
    val isAgendaPage: Boolean,
    val hasAnyFilters: Boolean
)
