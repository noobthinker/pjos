package com.pj109.xkorey.pjos.ui.maze

import com.pj109.xkorey.share.di.ChildFragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
internal abstract class MazeFragmentViewDiModel {

    @ChildFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeMazeFragment(): MazeFragment
}