package com.pj109.xkorey.share.domain.signin

import com.pj109.xkorey.model.room.UseAppSettings
import com.pj109.xkorey.share.domain.MediatorUseCase
import com.pj109.xkorey.share.domain.room.AppConfigQueryUseCase
import com.pj109.xkorey.share.domain.room.ConfigQueryUseCase
import javax.inject.Inject


open class ObserveUserSettingUseCase @Inject constructor(
    private val configQueryUseCase: ConfigQueryUseCase,
    private val appConfigQueryUseCase: AppConfigQueryUseCase
): MediatorUseCase<String, List<UseAppSettings>>() {

    override fun execute(parameters:String) {
        result.addSource(appConfigQueryUseCase.invoke(parameters)) {
            result.postValue(it)
        }
    }
}