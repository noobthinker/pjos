package com.pj109.xkorey.pjos.ui.login

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pj109.xkorey.model.room.UseAppSettings
import com.pj109.xkorey.share.domain.prefs.FunctionKeySetUseCase
import com.pj109.xkorey.share.domain.room.AppConfigQueryUseCase
import com.pj109.xkorey.share.domain.room.ConfigQueryUseCase
import com.pj109.xkorey.share.domain.signin.ObserveUserSettingUseCase
import com.pj109.xkorey.share.result.Event
import com.pj109.xkorey.share.result.Result
import com.pj109.xkorey.share.result.succeeded
import javax.inject.Inject

interface SignInViewModelDelegate {
    val currentUserImageUri: LiveData<Uri?>
    val performSignInEvent: MutableLiveData<Event<SignInEvent>>
    val shouldShowNotificationsPrefAction: LiveData<Event<Boolean>>
    val pushKey:LiveData<String>
    fun isSignedIn(): Boolean
    fun isRegistered(): Boolean
    fun getUserId(): String?
    fun emitSignInRequest()
    fun emitRegisterOk()
    fun emitSignOutRequest()
    fun emitAnonymousSignInRequest()
    fun emitLoopbar()
}


enum class SignInEvent {
    RequestSignIn, RequestSignOut,RequestAnonymous,RequestLoopbar,RequestRegisterOk
}


internal class RoomSignInViewModelDelegate @Inject constructor(
    private val observable: ObserveUserSettingUseCase,
    private val configQueryUseCase: AppConfigQueryUseCase,
    private val queryUseCase: ConfigQueryUseCase
) : SignInViewModelDelegate {

    private val _pushKey = MutableLiveData<String>()
    override val pushKey: LiveData<String>
        get() = _pushKey


    private val _sign = MutableLiveData<Boolean>()
    val sign:LiveData<Boolean>
        get() = _sign
    private val _imageUri = MutableLiveData<Uri>()
    override val currentUserImageUri: LiveData<Uri?>
        get() = _imageUri
    override val performSignInEvent = MutableLiveData<Event<SignInEvent>>()
    override val shouldShowNotificationsPrefAction: LiveData<Event<Boolean>>
        get() = shouldShowNotificationsPrefAction
    private val _configList = MutableLiveData<List<UseAppSettings>>()

    init {
        val confList = configQueryUseCase.invoke("user")
        confList.observeForever {
            when{
                it.succeeded->{
                    val dataList = (it as Result.Success).data
                    _configList.postValue(dataList)
                    var isLogin=false
                    dataList.map {
                        if(it.configKey.equals("sign")){
                            it.configVal?.let {
                                isLogin=it.isNotBlank()
                            }
                        }
                        if(it.configKey.equals("avatar")){
                            _imageUri.postValue(Uri.parse(it.configVal))
                        }
                    }
                    _sign.postValue(isLogin)
                }
            }
        }
    }

    override fun isSignedIn(): Boolean {
        sign.value?.let {
            return it
        }
        return false
    }

    override fun isRegistered(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserId(): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun emitSignInRequest() {
        performSignInEvent.postValue(Event(SignInEvent.RequestSignIn))
    }

    override fun emitSignOutRequest() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun emitAnonymousSignInRequest() {
        performSignInEvent.postValue(Event(SignInEvent.RequestAnonymous))
    }

    override fun emitLoopbar(){
        performSignInEvent.postValue(Event(SignInEvent.RequestLoopbar))
    }

    override fun emitRegisterOk(){
        performSignInEvent.postValue(Event(SignInEvent.RequestRegisterOk))
        _sign.postValue(true)
    }

}
