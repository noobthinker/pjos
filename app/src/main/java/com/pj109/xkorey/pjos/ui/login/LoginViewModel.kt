package com.pj109.xkorey.pjos.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pj109.xkorey.share.result.Event
import timber.log.Timber
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    signInViewModelDelegate: SignInViewModelDelegate
): ViewModel(),SignInViewModelDelegate by signInViewModelDelegate {

    private val _dismissDialogAction = MutableLiveData<Event<Unit>>()
    val dismissDialogAction: LiveData<Event<Unit>>
        get() = _dismissDialogAction

    fun onSignIn() {
        Timber.d("Sign in requested")
        emitSignInRequest()
        _dismissDialogAction.value = Event(Unit)
    }

    fun onAnonymous(){
        emitAnonymousSignInRequest()
        _dismissDialogAction.value = Event(Unit)
    }

    fun onShowLoopbar(){
        emitLoopbar()
        _dismissDialogAction.value = Event(Unit)
    }

    fun onSignOut() {
        Timber.d("Sign out requested")
        _dismissDialogAction.value = Event(Unit)
    }

    fun onCancel() {
        _dismissDialogAction.value = Event(Unit)
    }
}
