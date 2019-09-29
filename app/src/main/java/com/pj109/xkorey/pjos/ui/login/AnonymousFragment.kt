package com.pj109.xkorey.pjos.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pj109.xkorey.pjos.databinding.AnonymousFragmentBinding
import com.pj109.xkorey.share.util.viewModelProvider
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class AnonymousFragment : BottomSheetDialogFragment(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    lateinit var anonymousViewModel: AnonymousViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        anonymousViewModel=viewModelProvider(viewModelFactory)
        val binding = AnonymousFragmentBinding.inflate(inflater, container, false).apply {
            viewModel=anonymousViewModel
        }
        return binding.root
    }

    companion object {
        fun newInstance() = AnonymousFragment()
    }
}
