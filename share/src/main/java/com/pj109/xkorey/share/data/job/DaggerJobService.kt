package com.pj109.xkorey.share.data.job

import android.app.job.JobService
import dagger.android.AndroidInjection

abstract class DaggerJobService: JobService() {
    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this)
    }
}