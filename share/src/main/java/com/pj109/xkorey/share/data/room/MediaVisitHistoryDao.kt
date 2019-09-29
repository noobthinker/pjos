package com.pj109.xkorey.share.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.pj109.xkorey.model.room.MediaVisitHistory

@Dao
interface MediaVisitHistoryDao:RDao<MediaVisitHistory> {

    @Query("select * from MediaVisitHistory")
    fun getAll(): LiveData<List<MediaVisitHistory>>
}