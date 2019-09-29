package com.pj109.xkorey.pjos.ui.login


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.pj109.xkorey.pjos.R
import com.pj109.xkorey.pjos.databinding.LoopbarKeyLoginFragmentBinding

class LoopbarKeyLoginFragment: LoopbarLoginFragment() {

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?, model: ViewModel): ViewDataBinding {
        val binding = LoopbarKeyLoginFragmentBinding.inflate(inflater,container,false).apply {
            viewModel=model as SignInViewModel
        }
        setOnClickBackImgBtnClick(binding.loopbar.up)
        return  binding
    }

    override fun getTitle(): Int {
        return R.string.loopbar_user_key_login
    }

    companion object {
        fun newInstance() = LoopbarKeyLoginFragment()
    }

}
