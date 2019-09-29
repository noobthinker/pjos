package com.pj109.xkorey.pjos.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pj109.xkorey.share.domain.prefs.OnboardingCompletedUseCase
import com.pj109.xkorey.share.util.map
import com.pj109.xkorey.share.result.Event
import com.pj109.xkorey.share.result.Result
import javax.inject.Inject

class LaunchViewModel @Inject constructor(
    onboardingCompletedUseCase: OnboardingCompletedUseCase
) : ViewModel() {
    private val onboardingCompletedResult = MutableLiveData<Result<Boolean>>()
    val launchDestination: LiveData<Event<LaunchDestination>>

    init {
        onboardingCompletedUseCase(Unit, onboardingCompletedResult)
        launchDestination = onboardingCompletedResult.map {
            if ((it as? Result.Success)?.data == false) {
                Event(LaunchDestination.ONBOARDING)
            } else {
                Event(LaunchDestination.MAIN_ACTIVITY)
            }
        }
    }

}

enum class LaunchDestination {
    ONBOARDING,
    MAIN_ACTIVITY
}