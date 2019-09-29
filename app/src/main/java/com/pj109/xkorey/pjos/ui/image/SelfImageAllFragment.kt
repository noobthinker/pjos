package com.pj109.xkorey.pjos.ui.image

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NavUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pj109.xkorey.pjos.databinding.SelfImageAllFragmentBinding
import com.pj109.xkorey.pjos.ui.image.adapter.RecyclerViewItemsAdapter
import com.pj109.xkorey.share.util.oCacheOut
import com.pj109.xkorey.share.util.viewModelProvider
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class SelfImageAllFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var selfImageAllViewModel: SelfImageAllViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        selfImageAllViewModel = viewModelProvider(viewModelFactory)
        selfImageAllViewModel.getAllImage()
        val itemsAdapter = RecyclerViewItemsAdapter()
        val binding = SelfImageAllFragmentBinding.inflate(inflater, container, false).apply{
            setLifecycleOwner(this@SelfImageAllFragment)
            viewModel=selfImageAllViewModel
        }


        binding.recyclerView.run {
            adapter = itemsAdapter
            itemAnimator?.run {
                addDuration = 120L
                moveDuration = 120L
                changeDuration = 120L
                removeDuration = 100L
            }
        }

        selfImageAllViewModel.userImage.observe(this, Observer{
            itemsAdapter.images = it ?: emptyList()
        })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}


