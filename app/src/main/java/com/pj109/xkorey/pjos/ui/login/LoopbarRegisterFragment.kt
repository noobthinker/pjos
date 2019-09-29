package com.pj109.xkorey.pjos.ui.login


import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

import com.pj109.xkorey.pjos.R
import com.pj109.xkorey.pjos.databinding.LoopbarUserRegisterFragmentBinding


class LoopbarRegisterFragment : LoopbarLoginFragment() {

    override fun getTitle(): Int {
        return R.string.loopbar_user_register
    }

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?, model: ViewModel): ViewDataBinding {
        val binding = LoopbarUserRegisterFragmentBinding.inflate(inflater,container,false).apply {
            viewModel = model as SignInViewModel
        }
        setOnClickBackImgBtnClick(binding.loopbar.up)
        binding.viewModel?.let {
            val vm = it
            vm.initRegParam()
            it.smsCountDown.observe(this, Observer {
                vm.changeCountDown(it)
            })
            vm.showRegCodeNot(binding.markdownView)
            // 显示错误提示
            vm.toastMessage.observe(this, Observer {
                Toast.makeText(this.context, it, Toast.LENGTH_LONG).show()
            })
        }

        return binding
    }

    companion object {
        fun newInstance() = LoopbarRegisterFragment()
    }

}
