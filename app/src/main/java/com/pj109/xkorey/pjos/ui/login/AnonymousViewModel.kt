package com.pj109.xkorey.pjos.ui.login

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pj109.xkorey.model.room.UseAppSettings
import com.pj109.xkorey.share.domain.room.AppConfigQueryUseCase
import com.pj109.xkorey.share.domain.room.ConfigQueryUseCase
import com.pj109.xkorey.share.domain.room.ConfigSetUseCase
import com.pj109.xkorey.share.domain.signin.ObserveUserSettingUseCase
import com.pj109.xkorey.share.result.Result
import com.pj109.xkorey.share.result.succeeded
import com.pj109.xkorey.share.util.map
import timber.log.Timber
import javax.inject.Inject

class AnonymousViewModel @Inject constructor(
    private val configQueryUseCase: ConfigQueryUseCase,
    private val observable: ObserveUserSettingUseCase,
    private val configSetUseCase: ConfigSetUseCase
) : ViewModel() {

    val nickName= ObservableField<String>()
    val errorText=ObservableField<String>()

    init {
        val historyName = configQueryUseCase.invoke(Pair("anonymous","connect"))
        historyName.observeForever {
            nickName.set((it as Result.Success).data)
        }
    }

    fun connect(){
        errorText.set("昵称不合法")
        configSetUseCase.invoke(Triple("anonymous","connect",nickName.get()!!))
        Timber.i("name :${nickName.get()}")
    }

}
