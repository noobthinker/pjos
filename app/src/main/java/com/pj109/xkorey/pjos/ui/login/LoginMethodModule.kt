package com.pj109.xkorey.pjos.ui.login

import com.pj109.xkorey.share.di.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class LoginMethodModule {


    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeLoopbarPhoneLoginFragment(): LoopbarPhoneLoginFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeLoopbarKeyLoginFragment(): LoopbarKeyLoginFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeLoopbarAgreementFragment():LoopbarAgreementFragment


    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeLoopbarRegisterFragment():LoopbarRegisterFragment


    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeLoopbarAnonymousFragment():LoopbarAnonymousFragment









}