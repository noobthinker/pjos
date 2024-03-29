package com.pj109.xkorey.pjos.ui.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.pj109.xkorey.pjos.R

class SignOutFragment : Fragment() {

    companion object {
        fun newInstance() = SignOutFragment()
    }

    private lateinit var viewModel: SignOutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sign_out_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SignOutViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
