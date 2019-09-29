package com.pj109.xkorey.share.domain.prefs

import com.pj109.xkorey.share.data.prefs.PreferenceStorage
import com.pj109.xkorey.share.domain.UseCase
import com.pj109.xkorey.share.domain.config.TagMatcher
import javax.inject.Inject

/** Loads filter selections from persistent storage into a [UserSessionMatcher]. */
class LoadSelectedFiltersUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : UseCase<TagMatcher, Unit>() {

    override fun execute(parameters: TagMatcher) {
        parameters.load(preferenceStorage)
    }
}
