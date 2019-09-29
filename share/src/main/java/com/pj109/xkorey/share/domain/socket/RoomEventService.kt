package com.pj109.xkorey.share.domain.socket

import androidx.lifecycle.MediatorLiveData
import com.pj109.xkorey.model.enums.RoomEvent
import com.pj109.xkorey.share.data.NettyAllInOne
import com.pj109.xkorey.share.data.SocketDataPro
import timber.log.Timber
import com.pj109.xkorey.share.result.Result
import com.pj109.xkorey.share.result.succeeded


class RoomEventService(
    private val viewModel: RefreshViewModel
) : SocketMessageService {


//    private val _socketData= MediatorLiveData<Result<NettyAllInOne.AllInone>>()

//    @Inject
//    lateinit var imageSaveUseCase: ImageSaveUseCase
//
//    @Inject
//    lateinit var videoSaveUseCase: VideoSaveUseCase
//
//    @Inject
//    lateinit var tagSaveUseCase: TagSaveUseCase
//
//    @Inject
//    lateinit var mazeSaveUseCase: MazeSaveUseCase

//    init {
//        _socketData.addSource(viewModel.socketData,{
//            when{
//                it.succeeded->{
//                    onMessageReceived((it as Result.Success).data)
//                }
//            }
//        })
//    }


    override fun onMessageReceived(message: NettyAllInOne.AllInone) {
        // 校验是否注册客户端
        Timber.i("socket response ${message.type} ")
        val data=SocketDataPro.SocketData.parseFrom(message.data)
        Timber.i("type : ${data.dataType.name}")
        Timber.i("response :${data.data.toStringUtf8()}")
        // parse type to roomEvent
        //save db
        //call viewModel
        viewModel.refresh(RoomEvent.HeartBeat)
    }



}