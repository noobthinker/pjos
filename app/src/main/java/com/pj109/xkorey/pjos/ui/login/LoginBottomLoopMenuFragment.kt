package com.pj109.xkorey.pjos.ui.login

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.view.menu.MenuBuilder
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.cleveroad.loopbar.adapter.ILoopBarPagerAdapter
import com.cleveroad.loopbar.widget.LoopBarView
import com.pj109.xkorey.pjos.MainNavigationFragment
import com.pj109.xkorey.pjos.R
import com.pj109.xkorey.pjos.databinding.LoginBottomLoopMenuFragmentBinding
import com.pj109.xkorey.share.util.inTransaction
import com.pj109.xkorey.share.util.viewModelProvider
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.login_bottom_loop_menu_fragment.*
import timber.log.Timber
import javax.inject.Inject

class LoginBottomLoopMenuFragment: DaggerFragment(), MainNavigationFragment {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var loopbarModel: LoginBottomLoopMenuViewModel

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loopbarModel = viewModelProvider(viewModelFactory)
        val binding = LoginBottomLoopMenuFragmentBinding.inflate(inflater, container, false).apply {
            viewModel=loopbarModel
            val adapter = LoginMethodAdapter(childFragmentManager,endlessView,showForm)
            showForm.adapter=adapter
            showForm.addOnPageChangeListener(adapter)
            endlessView.addOnItemClickListener {
                showForm.setCurrentItem(it)
            }
            Timber.i("view height ${placeHolder.height}")
        }
        return binding.root
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    companion object {
        fun newInstance() = LoginBottomLoopMenuFragment()
    }
}


class LoginMethodAdapter (fragmentManager: FragmentManager,val loobar: LoopBarView,val viewPager:ViewPager) : FragmentPagerAdapter(fragmentManager),ViewPager.OnPageChangeListener{

    override fun onPageScrollStateChanged(state: Int) {
        Timber.i("do nothing")
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        Timber.i("do nothing")
    }

    override fun onPageSelected(position: Int) {
        loobar.setCurrentItem(position)
    }


    override fun getItem(position: Int): Fragment {
        loobar.setCurrentItem(viewPager.currentItem)
        when(position){
            // 验证码登陆
            0->{
                return LoopbarPhoneLoginFragment.newInstance()
            }
            // 匿名使用
            3->{
                return LoopbarAnonymousFragment.newInstance()
            }
            // 协议
            4->{
                val fragment = LoopbarAgreementFragment.newInstance()
                return fragment
            }
            // key 登陆
            1->{
                return LoopbarKeyLoginFragment.newInstance()
            }
            // 注册
            2->{
                return LoopbarRegisterFragment.newInstance()
            }
            else->{
                return LoopbarPhoneLoginFragment.newInstance()
            }

        }
        return LoopbarPhoneLoginFragment.newInstance()
    }

    override fun getCount(): Int {
        return 5
    }


}
