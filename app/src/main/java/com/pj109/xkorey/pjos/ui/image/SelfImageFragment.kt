package com.pj109.xkorey.pjos.ui.image

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.pj109.xkorey.pjos.MainNavigationFragment
import com.pj109.xkorey.pjos.R
import com.pj109.xkorey.pjos.databinding.SelfImageFragmentBinding
import dagger.android.support.DaggerFragment


class SelfImageFragment : DaggerFragment(), MainNavigationFragment {


    private lateinit var binding: SelfImageFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SelfImageFragmentBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner(this@SelfImageFragment)
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            viewpager.offscreenPageLimit = PAGES.size
            viewpager.adapter = ImageAdapter(childFragmentManager)
            tabs.setupWithViewPager(binding.viewpager)
            viewpager.pageMargin = R.dimen.spacing_normal
            viewpager.setPageMarginDrawable(R.drawable.page_margin)
            viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {}
                override fun onPageScrolled(position: Int, offset: Float, offsetPixels: Int) {}
                override fun onPageSelected(position: Int) {
                }
            })
        }
    }


    inner class ImageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getCount() = PAGES.size

        override fun getItem(position: Int) = PAGES[position]()

        override fun getPageTitle(position: Int): CharSequence {
            return resources.getString(TITLES[position])
        }
    }

    companion object {
        fun newInstance() = SelfImageFragment()

        private val TITLES = arrayOf(
            R.string.image_all_title,
            R.string.image_folder_title,
            R.string.image_about_title,
            R.string.image_settings_title
        )

        private val PAGES = arrayOf(
            { SelfImageAllFragment() },
            { SelfImageFolderFragment() },
            { SelfImageAboutFragment() },
            { SelfImageSettingsFragment() }
        )



    }

}
