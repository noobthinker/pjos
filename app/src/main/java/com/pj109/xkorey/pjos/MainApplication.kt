package com.pj109.xkorey.pjos

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import com.pj109.xkorey.pjos.di.DaggerAppComponent
import com.pj109.xkorey.pjos.ui.login.SignInViewModel
import com.pj109.xkorey.pjos.util.CrashlyticsTree
import com.pj109.xkorey.share.data.job.RequestPoolJobService
import com.pj109.xkorey.share.util.cacheIn
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainApplication: DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashlyticsTree())
        }
        // ThreeTenBP for times and dates
        AndroidThreeTen.init(this)
        // start job
        startJob()
    }




    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }


    fun startJob(){
        cacheIn("netty","status","0")
        val serviceComponent = ComponentName(this, RequestPoolJobService::class.java)
        val builder = JobInfo.Builder(RequestPoolJobService.JOB_ID, serviceComponent)
            .setMinimumLatency(MINIMUM_LATENCY) // wait at least
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED) // Unmetered if possible
            .setOverrideDeadline(OVERRIDE_DEADLINE)
        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val result = jobScheduler.schedule(builder.build())
        if (result == JobScheduler.RESULT_FAILURE) {
            Timber.e(
                "job fail"
            )
        } else if (result == JobScheduler.RESULT_SUCCESS) {
            Timber.i("job scheduled..")
        }
    }


    companion object {
        private val MINIMUM_LATENCY = TimeUnit.SECONDS.toSeconds(5)
        private val OVERRIDE_DEADLINE = TimeUnit.SECONDS.toMinutes(15)
    }

}