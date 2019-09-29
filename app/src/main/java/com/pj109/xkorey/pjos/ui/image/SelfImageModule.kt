package com.pj109.xkorey.pjos.ui.image

import androidx.lifecycle.ViewModel
import com.pj109.xkorey.share.di.FragmentScoped
import com.pj109.xkorey.share.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class SelfImageModule {


    @FragmentScoped
    @ContributesAndroidInjector(modules = [SelfImageFragmentsModule::class])
    internal abstract fun selfImageFragment(): SelfImageFragment


    @Binds
    @IntoMap
    @ViewModelKey(SelfImageAllViewModel::class)
    abstract fun bindSelfImageAllViewModel(viewModel: SelfImageAllViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SelfImageSettingsViewModel::class)
    abstract fun bindSelfImageSettingsViewModel(viewModel: SelfImageSettingsViewModel): ViewModel




}