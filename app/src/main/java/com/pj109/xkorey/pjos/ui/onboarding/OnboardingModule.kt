package com.pj109.xkorey.pjos.ui.onboarding

import androidx.lifecycle.ViewModel
import com.pj109.xkorey.share.di.FragmentScoped
import com.pj109.xkorey.share.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Module where classes needed to create the [OnboardingFragment] are defined.
 */
@Module
internal abstract class OnboardingModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeOnboardingFragment(): OnboardingFragment

    /**
     * The ViewModels are created by Dagger in a map. Via the @ViewModelKey, we define that we
     * want to get a [OnboardingViewModel] class.
     */
    @Binds
    @IntoMap
    @ViewModelKey(OnboardingViewModel::class)
    internal abstract fun bindOnboardingViewModel(viewModel: OnboardingViewModel): ViewModel
}
