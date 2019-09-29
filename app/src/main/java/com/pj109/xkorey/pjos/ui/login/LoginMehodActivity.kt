package com.pj109.xkorey.pjos.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.pj109.xkorey.pjos.MainActivity
import com.pj109.xkorey.pjos.R
import com.pj109.xkorey.share.result.EventObserver
import com.pj109.xkorey.share.util.inTransaction
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class LoginMehodActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_mehod)

        if (savedInstanceState == null) {
            supportFragmentManager.inTransaction {
                add(R.id.login_method_container, LoginBottomLoopMenuFragment.newInstance())
            }
        }

        loginViewModel.performSignInEvent.observe(this, EventObserver { signInRequest ->
            // 注册成功返回app首页
            if(signInRequest==SignInEvent.RequestRegisterOk){
                startActivity(MainActivity.starterIntent(applicationContext))
            }
        })


    }

    companion object{
        private const val EXTRA_ID = "LOGIN_METHOD"

        fun starterIntent(context: Context): Intent {
            return Intent(context, LoginMehodActivity::class.java).apply {
            }
        }
    }
}
