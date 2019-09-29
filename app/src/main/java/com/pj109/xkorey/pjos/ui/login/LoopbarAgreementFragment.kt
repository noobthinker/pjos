package com.pj109.xkorey.pjos.ui.login


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.pj109.xkorey.pjos.R
import com.pj109.xkorey.pjos.databinding.LoopbarAgreementFragmentBinding
import kotlinx.android.synthetic.main.login_bottom_loop_menu_fragment.*
import timber.log.Timber


class LoopbarAgreementFragment : LoopbarLoginFragment() {

    override fun getTitle(): Int {
        return R.string.agreement
    }

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?, model: ViewModel): ViewDataBinding {
        val binding = LoopbarAgreementFragmentBinding.inflate(inflater,container,false).apply {
            viewModel=model as SignInViewModel
        }
        binding.viewModel?.let { it.showAgreementTxt(binding.markdownView) }
        return  binding
    }

    var height = 0

    companion object {
        fun newInstance() = LoopbarAgreementFragment()
    }


}
