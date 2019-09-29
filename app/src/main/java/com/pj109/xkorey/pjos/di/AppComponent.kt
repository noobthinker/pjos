package com.pj109.xkorey.pjos.di

import com.pj109.xkorey.pjos.MainApplication
import com.pj109.xkorey.pjos.ui.login.SignModel
import com.pj109.xkorey.share.di.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        AppModule::class,
        ViewModelModule::class,
        ServiceBindingModule::class,
        RoomModule::class,
        SignModel::class
    ]
)
interface AppComponent: AndroidInjector<MainApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MainApplication>()
}