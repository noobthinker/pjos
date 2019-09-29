package com.pj109.xkorey.pjos.ui.image

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pj109.xkorey.model.enums.MediaType
import com.pj109.xkorey.model.enums.RoomType
import com.pj109.xkorey.model.room.Media
import com.pj109.xkorey.share.domain.internal.DefaultScheduler
import com.pj109.xkorey.share.domain.room.MediaRoomOperationUseCase
import com.pj109.xkorey.share.result.Result
import com.pj109.xkorey.share.result.succeeded
import com.pj109.xkorey.share.util.Client
import com.pj109.xkorey.share.util.oCacheIn
import timber.log.Timber
import javax.inject.Inject

class SelfImageAllViewModel @Inject constructor(
    private val mediaImageGetAllUseCase: MediaRoomOperationUseCase
): ViewModel() {

    private val _userImage = MutableLiveData<List<Media>>()
    val userImage:LiveData<List<Media>>
        get() = _userImage

    fun getAllImage(){
        val mediaQuery = Media.onlyType(MediaType.MediaImage)
        val result =  mediaImageGetAllUseCase.invoke(Pair(mediaQuery,RoomType.Query))
        result.observeForever {
            when{
                it.succeeded -> {
                    _userImage.postValue((it as Result.Success).data)
                    makeData()
                }
                else ->{
                    Timber.e("error with query data.")
                }
            }
        }
    }

    fun makeData(){
        DefaultScheduler.execute {
            var i=0;
            var list = emptyList<Media>()
            while (i<10){
                Timber.i("add data")
                val image = Media(i,"url"+i,"tag"+i,null,null,null,null,null,null,null,null)
                list+=image
                _userImage.postValue(list)
                i+=1
                Thread.sleep(1000*3)
            }
            oCacheIn("test","test","workdone")
            Timber.i("socket work status :${Client.INSTANCE.isRunning}")
            if(Client.INSTANCE.isRunning){
                Timber.i("send socket message")
            }
        }
    }


}
