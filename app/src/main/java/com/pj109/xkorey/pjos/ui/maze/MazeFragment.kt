package com.pj109.xkorey.pjos.ui.maze

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.pj109.xkorey.pjos.MainNavigationFragment
import com.pj109.xkorey.pjos.R
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MazeFragment : DaggerFragment(), MainNavigationFragment {

    companion object {
        fun newInstance() = MazeFragment()
    }

    private lateinit var viewModel: MazeViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.maze_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
