package com.pj109.xkorey.pjos.ui.image

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.pj109.xkorey.pjos.R
import dagger.android.support.DaggerFragment

class SelfImageSettingsFragment : DaggerFragment() {

    companion object {
        fun newInstance() = SelfImageSettingsFragment()
    }

    private lateinit var viewModel: SelfImageSettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.self_image_settings_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(SelfImageSettingsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
