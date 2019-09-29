package com.pj109.xkorey.pjos.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pj109.xkorey.pjos.MainActivity
import com.pj109.xkorey.pjos.R
import com.pj109.xkorey.pjos.databinding.LoginFragmentBinding
import com.pj109.xkorey.pjos.ui.media.MediaFragment
import com.pj109.xkorey.pjos.widget.CustomDimDialogFragment
import com.pj109.xkorey.share.result.EventObserver
import com.pj109.xkorey.share.util.inTransaction
import com.pj109.xkorey.share.util.viewModelProvider
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class GateFragment : CustomDimDialogFragment(), HasSupportFragmentInjector {


    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    private lateinit var loginViewModel: LoginViewModel



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
    ): View {
        loginViewModel=viewModelProvider(viewModelFactory)
        val binding = LoginFragmentBinding.inflate(inflater, container, false).apply {
            viewModel=loginViewModel
        }


        loginViewModel.performSignInEvent.observe(this, EventObserver { signInRequest ->
            if (signInRequest == SignInEvent.RequestSignIn) {
                val dialog = SignInFragment()
                dialog.show(requireActivity().supportFragmentManager,DIALOG_SIGN_IN)
            }
            if(signInRequest == SignInEvent.RequestLoopbar){
                startActivity(LoginMehodActivity.starterIntent(requireContext()))
//                val loopbar  = LoginBottomLoopMenuFragment()
//                requireActivity().supportFragmentManager.inTransaction{
//                    replace(FRAGMENT_ID,loopbar)
//                }
            }
            if(signInRequest == SignInEvent.RequestAnonymous){
                val dialog = AnonymousFragment()
                dialog.show(requireActivity().supportFragmentManager,Anonymous_DIALOG_SIGN_IN)
            }
        })

        loginViewModel.dismissDialogAction.observe(this, EventObserver {
            dismiss()
        })


        return binding.root
    }

    companion object {
        fun newInstance() = GateFragment()
        private const val FRAGMENT_ID = R.id.fragment_container
        private val DIALOG_SIGN_IN="dialog-sign"
        private val DIALOG_SIGN_IN_LOOPBAR="login-bottom-loopbar"
        private val Anonymous_DIALOG_SIGN_IN="anonymous-dialog-sign"
    }

}
