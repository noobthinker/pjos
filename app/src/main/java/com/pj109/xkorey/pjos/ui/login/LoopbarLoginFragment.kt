package com.pj109.xkorey.pjos.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.app.NavUtils
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pj109.xkorey.model.data.DialogType
import com.pj109.xkorey.model.data.DialogVo
import com.pj109.xkorey.model.enums.EventMthod
import com.pj109.xkorey.pjos.util.errorDialog
import com.pj109.xkorey.share.util.viewModelProvider
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

abstract class LoopbarLoginFragment: DaggerFragment(){


    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        signinViewModel=viewModelProvider(viewModelFactory)
        signinViewModel.loopbarTitle.set(getTitle())
        signinViewModel.buttonTxt.set(getTitle())
        signinViewModel.eventType= EventMthod.Login
        signinViewModel.awesomeDialog.observe(this, Observer {
            Timber.i("dialog show")
            when (it) {
                DialogType.error->{
                    signinViewModel.awesomeDialogStatus?.let {
                        if(it.value==DialogType.none){
                            Timber.i("dialog show")
                            signinViewModel.awesomeDialogStatus.postValue(it.value)
                            val vo = DialogVo(it.value!!,getString(getTitle()),signinViewModel.toastMessage.value!!,"确定",signinViewModel.awesomeDialogStatus,this.context!!)
                            errorDialog(vo)
                        }
                    }
                }
            }
        })
        val binding=inflate(inflater, container, signinViewModel)
        return binding.root
    }

    fun setOnClickBackImgBtnClick(btn:ImageButton){
        btn.setOnClickListener {
            NavUtils.navigateUpFromSameTask(requireActivity())
        }
    }


    @Inject
    lateinit var signinViewModel: SignInViewModel

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    abstract fun getTitle():Int

    abstract fun inflate(inflater: LayoutInflater,
                         container: ViewGroup?,
                         model: ViewModel): ViewDataBinding

}