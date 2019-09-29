package com.pj109.xkorey.pjos.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pj109.xkorey.share.domain.prefs.OnboardingCompleteActionUseCase
import com.pj109.xkorey.share.result.Event
import javax.inject.Inject

class OnboardingViewModel @Inject constructor(
    private val onboardingCompleteActionUseCase: OnboardingCompleteActionUseCase
): ViewModel() {

    private val _navigateToMainActivity = MutableLiveData<Event<Unit>>()
    val navigateToMainActivity: LiveData<Event<Unit>> = _navigateToMainActivity

    fun getStartedClick() {
        onboardingCompleteActionUseCase(true)
        _navigateToMainActivity.postValue(Event(Unit))
    }

    fun  refreshWelcomeFlag(){
        onboardingCompleteActionUseCase(false)
        _navigateToMainActivity.postValue(Event(Unit))
    }

}
