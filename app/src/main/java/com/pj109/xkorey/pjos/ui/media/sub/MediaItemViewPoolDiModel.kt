package com.pj109.xkorey.pjos.ui.media.sub

import androidx.recyclerview.widget.RecyclerView
import com.pj109.xkorey.share.di.FragmentScoped
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
internal class MediaItemViewPoolDiModel {

    @FragmentScoped
    @Provides
    @Named("mediaViewPool")
    fun providesSessionViewPool(): RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    @FragmentScoped
    @Provides
    @Named("tagViewPool")
    fun providesTagViewPool(): RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

}