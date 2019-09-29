package com.pj109.xkorey.share.data.room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface RDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg t:T)

    @Delete
    fun  delete(vararg t:T)

}