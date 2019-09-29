package com.pj109.xkorey.share.domain.prefs

import com.pj109.xkorey.share.data.prefs.PreferenceStorage
import com.pj109.xkorey.share.domain.UseCase
import javax.inject.Inject

class FunctionKeyGetUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : UseCase<Unit, String>(){
    override fun execute(parameters: Unit): String {
        preferenceStorage.functionKey?.let {
            return it
        }
        return ""
    }
}