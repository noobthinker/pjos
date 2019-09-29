package com.pj109.xkorey.share.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ShowViewModelFactory):
        ViewModelProvider.Factory
}
