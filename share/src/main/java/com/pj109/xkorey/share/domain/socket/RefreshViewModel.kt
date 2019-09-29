package com.pj109.xkorey.share.domain.socket

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.pj109.xkorey.model.enums.RoomEvent
import com.pj109.xkorey.share.data.NettyAllInOne
import com.pj109.xkorey.share.result.Result

abstract class RefreshViewModel(
    private val nettyClientUseCase: NettyClientUseCase
): ViewModel() {

    val socketData:MediatorLiveData<Result<NettyAllInOne.AllInone>>


    init {
        socketData=nettyClientUseCase.observe()
    }


    abstract fun refresh(event: RoomEvent)
}