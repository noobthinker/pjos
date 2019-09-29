package com.pj109.xkorey.pjos.ui.login

import androidx.lifecycle.ViewModel
import com.pj109.xkorey.share.di.ChildFragmentScoped
import com.pj109.xkorey.share.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class GateModel{


    @ChildFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeGateDialogFragment(): GateFragment

    @ChildFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeSignInDialogFragment(): SignInFragment


    @ChildFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeAnonymousSignInDialogFragment(): AnonymousFragment


    @ChildFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeLoginBottomLoopMenuFragment(): LoginBottomLoopMenuFragment



    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindGateViewModel(viewModel: LoginViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    abstract fun bindSignInViewModel(viewModel: SignInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AnonymousViewModel::class)
    abstract fun bindAnonymousSignInViewModel(viewModel: AnonymousViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(LoginBottomLoopMenuViewModel::class)
    abstract fun bindLoginBottoMLoopMenuViewModel(viewModel: LoginBottomLoopMenuViewModel): ViewModel


}