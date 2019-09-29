package com.pj109.xkorey.pjos.di

import com.pj109.xkorey.pjos.MainActivity
import com.pj109.xkorey.pjos.ui.LaunchModule
import com.pj109.xkorey.pjos.ui.LauncherActivity
import com.pj109.xkorey.pjos.ui.image.SelfImageModule
import com.pj109.xkorey.pjos.ui.login.GateModel
import com.pj109.xkorey.pjos.ui.login.LoginMehodActivity
import com.pj109.xkorey.pjos.ui.login.LoginMethodModule
import com.pj109.xkorey.pjos.ui.maze.MazeViewDiModel
import com.pj109.xkorey.pjos.ui.media.MediaViewDiModel
import com.pj109.xkorey.pjos.ui.onboarding.OnboardingActivity
import com.pj109.xkorey.pjos.ui.onboarding.OnboardingModule
import com.pj109.xkorey.pjos.ui.prefs.PreferenceModule
import com.pj109.xkorey.pjos.ui.tag.TagModel
import com.pj109.xkorey.share.di.ActivityScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [LaunchModule::class])
    internal abstract fun launcherActivity(): LauncherActivity


    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [PreferenceModule::class,SelfImageModule::class, MazeViewDiModel::class, MediaViewDiModel::class
        , GateModel::class, TagModel::class]
    )
    internal abstract fun mainActivity(): MainActivity


    @ActivityScoped
    @ContributesAndroidInjector(modules = [OnboardingModule::class])
    internal abstract fun onboardingActivity(): OnboardingActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [GateModel::class, LoginMethodModule::class])
    internal abstract fun loginMethodActivity(): LoginMehodActivity

}