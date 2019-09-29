package com.pj109.xkorey.pjos.ui.image

import com.pj109.xkorey.share.di.ChildFragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class SelfImageFragmentsModule {

    @ChildFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeSelfImageAboutFragment(): SelfImageAboutFragment

    @ChildFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeSelfImageAllFragment(): SelfImageAllFragment


    @ChildFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeSelfImageSettingsFragment(): SelfImageSettingsFragment

    @ChildFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeSelfImageFolderFragment(): SelfImageFolderFragment

}