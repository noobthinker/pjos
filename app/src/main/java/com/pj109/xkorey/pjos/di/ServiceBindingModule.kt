package com.pj109.xkorey.pjos.di

import com.google.samples.apps.iosched.shared.di.ServiceScoped
import com.pj109.xkorey.share.data.job.RequestPoolJobService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServiceBindingModule {

    @ServiceScoped
    @ContributesAndroidInjector
    internal abstract fun requestPoolJobService(): RequestPoolJobService


}