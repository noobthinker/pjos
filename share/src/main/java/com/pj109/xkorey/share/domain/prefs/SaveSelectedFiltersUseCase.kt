package com.pj109.xkorey.share.domain.prefs

import com.pj109.xkorey.share.data.prefs.PreferenceStorage
import com.pj109.xkorey.share.domain.UseCase
import com.pj109.xkorey.share.domain.config.TagMatcher
import javax.inject.Inject

class SaveSelectedFiltersUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : UseCase<TagMatcher, Unit>() {

    override fun execute(parameters: TagMatcher) {
        parameters.save(preferenceStorage)
    }
}
