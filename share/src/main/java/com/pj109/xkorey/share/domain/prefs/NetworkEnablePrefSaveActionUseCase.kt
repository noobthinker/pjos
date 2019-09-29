package com.pj109.xkorey.share.domain.prefs

import com.pj109.xkorey.share.data.prefs.PreferenceStorage
import com.pj109.xkorey.share.domain.UseCase
import javax.inject.Inject

open class NetworkEnablePrefSaveActionUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : UseCase<Boolean, Boolean>() {

    override fun execute(parameters: Boolean): Boolean {
        preferenceStorage.onnetworkEnable=parameters
        return preferenceStorage.onnetworkEnable
    }
}