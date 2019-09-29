package com.pj109.xkorey.share.domain.room

import com.pj109.xkorey.model.enums.RoomType
import com.pj109.xkorey.model.room.Media
import com.pj109.xkorey.share.data.room.MediaDao
import com.pj109.xkorey.share.domain.UseCase
import javax.inject.Inject

class MediaRoomOperationUseCase @Inject constructor(
    private val imageDao: MediaDao
) : UseCase<Pair<Media,RoomType>, List<Media>>()  {

    override fun execute(parameters: Pair<Media,RoomType>): List<Media> {
        val(media,type)=parameters
        when(type){
            RoomType.Query->{
                if(media.id!=null){
                    return listOf(imageDao.getById(media.id!!))
                }else{
                    return imageDao.getAll(media.mediaType!!)
                }
            }
            RoomType.Delete->{
                if(media.id!=null){
                    imageDao.delete(media)
                }else{
                    imageDao.clean(media.mediaType!!)
                }
            }
            RoomType.Update->{
                imageDao.delete(media)
                imageDao.insert(media)
            }
            RoomType.Add->{
                imageDao.insert(media)
            }
        }
        return emptyList()
    }

}