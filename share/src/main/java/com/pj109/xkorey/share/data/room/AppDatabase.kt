package com.pj109.xkorey.share.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pj109.xkorey.model.room.*
import com.pj109.xkorey.share.data.room.converter.DateConverter

@Database(entities = arrayOf(
    Tag::class,
    Media::class
, MediaVisitHistory::class
, UseAppSettings::class,MediaTags::class,Maze::class
),version = 6)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun mediaDao():MediaDao

    abstract fun historyDao():MediaVisitHistoryDao

    abstract fun tagDao():TagDao

    abstract fun settingsDao():UseAppSettingsDao

    abstract fun mediaTagsDao():MediaTagsDao

    abstract fun mazeDao():MazeDao

}