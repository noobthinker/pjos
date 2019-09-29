package com.pj109.xkorey.share.domain.socket

import com.pj109.xkorey.share.BuildConfig
import com.pj109.xkorey.share.data.NettyAllInOne
import com.pj109.xkorey.share.data.SocketDataPro
import com.pj109.xkorey.share.data.prefs.SharedPreferenceStorage
import com.pj109.xkorey.share.domain.MediatorUseCase
import com.pj109.xkorey.share.domain.internal.DefaultScheduler
import com.pj109.xkorey.share.domain.prefs.FunctionKeyGetUseCase
import com.pj109.xkorey.share.domain.room.AppConfigQueryUseCase
import com.pj109.xkorey.share.domain.room.ConfigQueryUseCase
import com.pj109.xkorey.share.result.Result
import com.pj109.xkorey.share.result.succeeded
import com.pj109.xkorey.share.util.Client
import com.pj109.xkorey.share.util.cacheIn
import com.pj109.xkorey.share.util.cacheOut
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class NettyClientUseCase @Inject constructor(
    private val appConfigUsecase: AppConfigQueryUseCase,
    private val keyGetUseCase: FunctionKeyGetUseCase,
    private val sharedPreferenceStorage: SharedPreferenceStorage
) : MediatorUseCase<String, NettyAllInOne.AllInone>() {

    override fun execute(parameters: String){
        DefaultScheduler.execute {
            val status = cacheOut("netty","status")
            if(status.isNullOrBlank() || "0".equals(status)){
                var host=BuildConfig.SocketHost
                var port=BuildConfig.SocketPort
                val config = appConfigUsecase.executeNow(parameters)
                when {
                    config.succeeded ->{
                        val list=(config as Result.Success)?.data
                        list.forEach {
                            if(it.configKey.equals("host")){
                                host=it.configVal!!
                            }
                            if(it.configKey.equals("port")){
                                port=it.configVal!!.toInt()
                            }
                        }
                    }
                }
                Timber.i("socket config :host ${host} port ${port}")
                Client.INSTANCE.startUp(host,port,this)
                val it = keyGetUseCase.executeNow(Unit)
                when {
                    it.succeeded->{
                        cacheIn("netty","status","1")
                        val key= (it as Result.Success).data
                        if(key.isNullOrBlank()){
                            Timber.i("key is null")
                        }else{
                            // 注册客户端
                            val data = SocketDataPro.SocketData.newBuilder().setKey(key).setEcho(1).build()
                            val heartBeat = NettyAllInOne.AllInone.newBuilder().setType("APP").setData(data.toByteString()).build()
                            Client.INSTANCE.send(heartBeat)
                        }
                    }
                }
            }
        }
    }


}
