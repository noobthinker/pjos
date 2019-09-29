package com.pj109.xkorey.share.data.room

import androidx.room.Dao
import androidx.room.Query
import com.pj109.xkorey.model.room.Media

@Dao
interface MediaDao:RDao<Media> {

    @Query("select * from Media where mediaType=:meidaType")
    fun getAll(meidaType:String):List<Media>

    @Query("select count(*) from Media where mediaType=:meidaType")
    fun count(meidaType:String):Int

    @Query("delete from Media where mediaType=:meidaType")
    fun clean(meidaType:String)

    @Query("select * from Media where id=:id")
    fun getById(id:Int):Media


}