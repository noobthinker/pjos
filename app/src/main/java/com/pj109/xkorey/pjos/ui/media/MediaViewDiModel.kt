package com.pj109.xkorey.pjos.ui.media

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.pj109.xkorey.pjos.ui.media.sub.MediaItemViewDiModel
import com.pj109.xkorey.pjos.ui.media.sub.MediaItemViewPoolDiModel
import com.pj109.xkorey.share.di.ChildFragmentScoped
import com.pj109.xkorey.share.di.FragmentScoped
import com.pj109.xkorey.share.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Named


@Module
abstract class MediaViewDiModel {


    @FragmentScoped
    @ContributesAndroidInjector(
        modules = [
            MediaItemViewDiModel::class,
            MediaItemViewPoolDiModel::class
        ]
    )
    internal abstract fun contributeMediaFragment(): MediaFragment



    @Binds
    @IntoMap
    @ViewModelKey(MediaViewModel::class)
    abstract fun bindMediaViewModel(viewModel: MediaViewModel): ViewModel


}