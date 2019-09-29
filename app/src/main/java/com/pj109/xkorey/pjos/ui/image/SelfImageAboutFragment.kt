package com.pj109.xkorey.pjos.ui.image


import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pj109.xkorey.pjos.R
import dagger.android.support.DaggerFragment


/**
 * A simple [Fragment] subclass.
 *
 */
class SelfImageAboutFragment : DaggerFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_self_image_about, container, false)
    }


}
