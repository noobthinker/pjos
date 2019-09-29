package com.pj109.xkorey.pjos.ui.prefs

import androidx.lifecycle.ViewModel
import com.pj109.xkorey.share.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class PreferenceModule {

    @Binds
    @IntoMap
    @ViewModelKey(NetworkPreferenceViewModel::class)
    abstract fun bindSnackbarPreferenceViewModel(viewModel: NetworkPreferenceViewModel): ViewModel
}