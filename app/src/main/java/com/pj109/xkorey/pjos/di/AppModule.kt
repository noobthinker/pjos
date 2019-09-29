package com.pj109.xkorey.pjos.di

import android.content.Context
import com.pj109.xkorey.pjos.MainApplication
import com.pj109.xkorey.share.data.job.DaggerJobService
import com.pj109.xkorey.share.data.job.RequestPoolJobService
import com.pj109.xkorey.share.data.prefs.PreferenceStorage
import com.pj109.xkorey.share.data.prefs.SharedPreferenceStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideContext(application: MainApplication): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun providesPreferenceStorage(context: Context): PreferenceStorage =
        SharedPreferenceStorage(context)

    @Singleton
    @Provides
    fun roomDataScheduler(): DaggerJobService = RequestPoolJobService()

}