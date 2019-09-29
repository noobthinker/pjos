package com.pj109.xkorey.pjos.di

import androidx.room.Room
import com.pj109.xkorey.pjos.MainApplication
import com.pj109.xkorey.share.data.room.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class RoomModule {

    @Singleton
    @Provides
    open fun appDatabase(application: MainApplication):AppDatabase =
        Room.databaseBuilder(
        application,
        AppDatabase::class.java, "pjos"
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    open fun imageDao(appDatabse: AppDatabase): MediaDao {
        return appDatabse.mediaDao()
    }

    @Singleton
    @Provides
    open fun configDao(appDatabse: AppDatabase): UseAppSettingsDao {
        return appDatabse.settingsDao()
    }

    @Singleton
    @Provides
    open fun mediaVisitHistoryDao(appDatabse: AppDatabase): MediaVisitHistoryDao {
        return appDatabse.historyDao()
    }

    @Singleton
    @Provides
    open fun mediaTagDao(appDatabse: AppDatabase): MediaTagsDao {
        return appDatabse.mediaTagsDao()
    }


    @Singleton
    @Provides
    open fun tagDao(appDatabse: AppDatabase): TagDao {
        return appDatabse.tagDao()
    }


}