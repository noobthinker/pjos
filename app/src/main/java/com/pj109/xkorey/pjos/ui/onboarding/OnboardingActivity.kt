package com.pj109.xkorey.pjos.ui.onboarding

import android.os.Bundle
import android.view.View
import com.pj109.xkorey.pjos.R
import com.pj109.xkorey.share.util.inTransaction
import dagger.android.support.DaggerAppCompatActivity

class OnboardingActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboarding_activity)
        val decor = window.decorView
        val flags = decor.systemUiVisibility or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        decor.systemUiVisibility = flags

        if (savedInstanceState == null) {
            supportFragmentManager.inTransaction {
                add(R.id.fragment_container, OnboardingFragment())
            }
        }
    }

}
