package com.pj109.xkorey.pjos.ui.prefs

import androidx.lifecycle.ViewModel
import com.pj109.xkorey.share.domain.prefs.NetworkEnablePrefSaveActionUseCase
import com.pj109.xkorey.share.domain.prefs.OnboardingCompleteActionUseCase
import com.pj109.xkorey.share.domain.prefs.OnboardingCompletedUseCase
import javax.inject.Inject

class NetworkPreferenceViewModel @Inject constructor(
    private val networkEnablePrefSaveActionUseCase: NetworkEnablePrefSaveActionUseCase,
    private val onboardingCompleteActionUseCase: OnboardingCompleteActionUseCase
): ViewModel(){

    fun stopNetwork(){
        networkEnablePrefSaveActionUseCase(false)
    }

    fun startNetwork(){
        networkEnablePrefSaveActionUseCase(true)
    }

    fun  refreshWelcome(){
        onboardingCompleteActionUseCase(false)
    }


}