package com.pj109.xkorey.share.data.room

import androidx.room.Dao
import androidx.room.Query
import com.pj109.xkorey.model.room.MediaTags

@Dao
interface MediaTagsDao:RDao<MediaTags> {

    @Query("select * from MediaTags where mediaId=:id")
    fun getAll(id:Int): List<MediaTags>

}