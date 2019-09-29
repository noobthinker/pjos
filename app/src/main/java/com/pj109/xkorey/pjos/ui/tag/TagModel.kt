package com.pj109.xkorey.pjos.ui.tag

import androidx.lifecycle.ViewModel
import com.pj109.xkorey.pjos.ui.login.GateFragment
import com.pj109.xkorey.pjos.ui.login.LoginViewModel
import com.pj109.xkorey.share.di.ChildFragmentScoped
import com.pj109.xkorey.share.di.FragmentScoped
import com.pj109.xkorey.share.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap


@Module
internal abstract class TagModel {


    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeTagFragment(): TagFragment

    @Binds
    @IntoMap
    @ViewModelKey(TagViewModel::class)
    abstract fun bindTagViewModel(viewModel: TagViewModel): ViewModel


}