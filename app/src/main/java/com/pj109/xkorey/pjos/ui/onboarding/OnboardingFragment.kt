package com.pj109.xkorey.pjos.ui.onboarding

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import com.pj109.xkorey.pjos.MainActivity
import com.pj109.xkorey.pjos.R
import com.pj109.xkorey.pjos.databinding.OnboardingFragmentBinding
import com.pj109.xkorey.share.util.viewModelProvider
import com.pj109.xkorey.share.result.EventObserver
import dagger.android.support.DaggerFragment
import javax.inject.Inject

private const val AUTO_ADVANCE_DELAY = 6_000L
private const val INITIAL_ADVANCE_DELAY = 3_000L


class OnboardingFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var onboardingViewModel: OnboardingViewModel

    private lateinit var pagerPager: ViewPagerPager

    private lateinit var binding: OnboardingFragmentBinding

    private val handler = Handler()

    private val advancePager: Runnable = object : Runnable {
        override fun run() {
            pagerPager.advance()
            handler.postDelayed(this, AUTO_ADVANCE_DELAY)
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        onboardingViewModel = viewModelProvider(viewModelFactory)

        binding = OnboardingFragmentBinding.inflate(inflater, container, false).apply {
            viewModel= onboardingViewModel
            setLifecycleOwner(this@OnboardingFragment)
            pager.adapter = OnboardingAdapter(childFragmentManager)
            pagerPager = ViewPagerPager(pager)
            pager.setOnTouchListener { _, _ ->
                handler.removeCallbacks(advancePager)
                false
            }
        }

        onboardingViewModel.navigateToMainActivity.observe(this, EventObserver {
            requireActivity().run {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })

        return binding.root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        handler.postDelayed(advancePager, INITIAL_ADVANCE_DELAY)
    }

    override fun onDetach() {
        handler.removeCallbacks(advancePager)
        super.onDetach()
    }
}

class OnboardingAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    // Don't show then countdown fragment if the conference has already started
    private val fragments =
        arrayOf(
            WelcomeFragment(),
            JoinUsFragment()
        )

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = fragments.size
}

