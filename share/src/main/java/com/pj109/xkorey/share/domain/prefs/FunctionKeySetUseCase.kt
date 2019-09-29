package com.pj109.xkorey.share.domain.prefs

import com.pj109.xkorey.share.data.prefs.PreferenceStorage
import com.pj109.xkorey.share.domain.UseCase
import javax.inject.Inject

open class FunctionKeySetUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : UseCase<String, Unit>(){
    override fun execute(parameters: String): Unit {
        preferenceStorage.functionKey=parameters
    }
}