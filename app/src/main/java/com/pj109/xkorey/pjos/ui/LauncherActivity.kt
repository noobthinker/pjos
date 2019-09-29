package com.pj109.xkorey.pjos.ui

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.pj109.xkorey.pjos.MainActivity
import com.pj109.xkorey.pjos.ui.onboarding.OnboardingActivity
import com.pj109.xkorey.share.util.checkAllMatched
import com.pj109.xkorey.share.util.viewModelProvider
import com.pj109.xkorey.share.result.EventObserver
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class LauncherActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: LaunchViewModel = viewModelProvider(viewModelFactory)

        viewModel.launchDestination.observe(this, EventObserver { destination ->
            when (destination) {
                LaunchDestination.MAIN_ACTIVITY -> startActivity(Intent(this, MainActivity::class.java))
                LaunchDestination.ONBOARDING -> startActivity(Intent(this, OnboardingActivity::class.java))
            }.checkAllMatched
            finish()
        })
    }
}
