package com.pj109.xkorey.share.data.job

import android.app.job.JobParameters
import com.pj109.xkorey.share.BuildConfig
import com.pj109.xkorey.share.domain.internal.DefaultScheduler
import com.pj109.xkorey.share.domain.room.ConfigQueryUseCase
import com.pj109.xkorey.share.domain.socket.NettyClientUseCase
import com.pj109.xkorey.share.domain.room.RoomLiveDataUseCase
import timber.log.Timber
import javax.inject.Inject

class RequestPoolJobService : DaggerJobService() {

    @Inject
    lateinit var roomLiveDataUseCase: RoomLiveDataUseCase

    @Inject
    lateinit var nettyClient: NettyClientUseCase


    override fun onStartJob(params: JobParameters?): Boolean {
        if(BuildConfig.NETWORKON){
            DefaultScheduler.execute {
                roomLiveDataUseCase.invoke(Unit)
                nettyClient.execute(BuildConfig.Socket)
            }
        }
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
//        Client.INSTANCE.close()
        Timber.i("socket job stop")
        return true
    }

    companion object {
        const val JOB_ID = 20190101
    }

}