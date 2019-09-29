package com.pj109.xkorey.pjos.ui.media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pj109.xkorey.model.room.Tag
import com.pj109.xkorey.pjos.MainNavigationFragment
import com.pj109.xkorey.pjos.R
import com.pj109.xkorey.pjos.databinding.MediaFragmentBinding
import com.pj109.xkorey.pjos.ui.common.fabVisibility
import com.pj109.xkorey.pjos.ui.login.GateFragment
import com.pj109.xkorey.pjos.ui.tag.TagViewModel
import com.pj109.xkorey.pjos.widget.BottomSheetBehavior
import com.pj109.xkorey.pjos.widget.BottomSheetBehavior.Companion.STATE_COLLAPSED
import com.pj109.xkorey.pjos.widget.BottomSheetBehavior.Companion.STATE_EXPANDED
import com.pj109.xkorey.pjos.widget.BottomSheetBehavior.Companion.STATE_HIDDEN
import com.pj109.xkorey.pjos.widget.FadingSnackbar
import com.pj109.xkorey.share.result.EventObserver
import com.pj109.xkorey.share.util.activityViewModelProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MediaFragment : DaggerFragment(), MainNavigationFragment {

    companion object {
        fun newInstance() = MediaFragment()
        private const val DIALOG_NEED_TO_SIGN_IN = "dialog_need_to_sign_in"
    }

    private lateinit var viewModel: MediaViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var tagViewModel: TagViewModel

    private lateinit var filtersFab: FloatingActionButton
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>
    private lateinit var snackbar: FadingSnackbar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = activityViewModelProvider(viewModelFactory)
        // login success 修改图标
        if (viewModel.isSignedIn()) {
//            binding.mediaBar.profileButton.setImageDrawable(resources.getDrawable(R.drawable.ic_account_login_circle))
            val draw = resources.getDrawable(R.drawable.ic_account_login_circle)
            viewModel.placeHolder.postValue(draw)
        } else {
            val draw = resources.getDrawable(R.drawable.ic_account_circle)
            viewModel.placeHolder.postValue(draw)
        }
        val binding = MediaFragmentBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner(this@MediaFragment)
            viewModel = this@MediaFragment.viewModel
        }

        viewModel.navigateToSignInDialogAction.observe(this, EventObserver {
            openSignInDialog()
        })

        viewModel.navigateToSignOutDialogAction.observe(this, EventObserver {

        })

        tagViewModel.transientUiState.observe(this, Observer {
            updateFiltersUi(it ?: return@Observer)
        })


        filtersFab = binding.filterFab
        snackbar=binding.snackbar
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bottomSheetBehavior = BottomSheetBehavior.from(view.findViewById(R.id.tag_sheet))
        filtersFab.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

//        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback {
//            override fun onStateChanged(bottomSheet: View, newState: Int) {
//                val a11yState = if (newState == STATE_EXPANDED) {
//                    View.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS
//                } else {
//                    View.IMPORTANT_FOR_ACCESSIBILITY_AUTO
//                }
//                viewPager.importantForAccessibility = a11yState
//                appbar.importantForAccessibility = a11yState
//            }
//        })

    }

    override fun onBackPressed(): Boolean {
        if (::bottomSheetBehavior.isInitialized && bottomSheetBehavior.state == STATE_EXPANDED) {
            // collapse or hide the sheet
            if (bottomSheetBehavior.isHideable && bottomSheetBehavior.skipCollapsed) {
                bottomSheetBehavior.state = STATE_HIDDEN
            } else {
                bottomSheetBehavior.state = STATE_COLLAPSED
            }
            return true
        }
        return super.onBackPressed()
    }

    private fun updateFiltersUi(uiState: TransientUiState) {
        val showFab = viewModel.isSignedIn() && !uiState.hasAnyFilters
        val hideable = uiState.isAgendaPage || !uiState.hasAnyFilters

        fabVisibility(filtersFab, showFab)
        // Set snackbar position depending whether fab/filters show.
        snackbar.updateLayoutParams<CoordinatorLayout.LayoutParams> {
            bottomMargin = resources.getDimensionPixelSize(
                if (showFab) {
                    R.dimen.snackbar_margin_bottom_fab
                } else {
                    R.dimen.bottom_sheet_peek_height
                }
            )
        }
        bottomSheetBehavior.isHideable = hideable
        bottomSheetBehavior.skipCollapsed = !uiState.hasAnyFilters
        if (hideable && bottomSheetBehavior.state == STATE_COLLAPSED) {
            bottomSheetBehavior.state = STATE_HIDDEN
        }
    }


    inner class Adapter(fm: FragmentManager, private val tags: List<Tag>) :
        FragmentPagerAdapter(fm) {

        override fun getCount() = tags.size

        override fun getItem(position: Int): Fragment {
            val t = tags[position]
            return MediaItemFragment.newInstance(t.id!!)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return tags[position].tagName!!
        }
    }

    private fun openSignInDialog() {
        val dialog = GateFragment()
        dialog.show(requireActivity().supportFragmentManager, DIALOG_NEED_TO_SIGN_IN)
    }


}



