package com.pj109.xkorey.share.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.pj109.xkorey.model.room.UseAppSettings

@Dao
interface UseAppSettingsDao:RDao<UseAppSettings> {

    @Query("select * from UseAppSettings")
    fun getAll(): LiveData<List<UseAppSettings>>

    @Query("delete from UseAppSettings")
    fun clean()

    @Query("select * from UseAppSettings where configType=:key")
    fun getConfig(key:String):List<UseAppSettings>


    @Query("select * from UseAppSettings where configType=:type and configKey=:key")
    fun getConfig(type:String,key:String):UseAppSettings

}