package com.pj109.xkorey.pjos.ui.maze

import androidx.lifecycle.ViewModel
import com.pj109.xkorey.share.di.FragmentScoped
import com.pj109.xkorey.share.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class MazeViewDiModel {

    @FragmentScoped
    internal abstract fun contributeMazeFragment(): MazeFragment


    @Binds
    @IntoMap
    @ViewModelKey(MazeViewModel::class)
    abstract fun bindMazeViewViewModel(viewModel: MazeViewModel): ViewModel




}
