package com.pj109.xkorey.pjos.ui.media.sub

import com.pj109.xkorey.pjos.ui.media.MediaItemFragment
import com.pj109.xkorey.share.di.ChildFragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class MediaItemViewDiModel {

    @ChildFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeMediaItemFragment(): MediaItemFragment

}