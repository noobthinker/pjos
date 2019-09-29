package com.pj109.xkorey.pjos.ui.media

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.pj109.xkorey.pjos.R
import com.pj109.xkorey.pjos.databinding.FragmentMediaItemBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import javax.inject.Named

private const val ARG_PARAM1 = "mediaTagId"

class MediaItemFragment : DaggerFragment() {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MediaViewModel

    private lateinit var binding:FragmentMediaItemBinding

    @Inject
    @field:Named("mediaViewPool")
    lateinit var mediaViewPool: RecyclerView.RecycledViewPool

    @Inject
    @field:Named("tagViewPool")
    lateinit var tagViewPool: RecyclerView.RecycledViewPool


    private var mediaTagId: Int? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mediaTagId = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_media_item, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        fun newInstance(param1: Int):MediaItemFragment{
            var args= Bundle().apply {
                putInt(ARG_PARAM1,param1)
            }
            return MediaItemFragment().apply { arguments = args }
        }
    }
}
