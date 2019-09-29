package com.pj109.xkorey.share.domain.digit

import androidx.lifecycle.MutableLiveData
import com.pj109.xkorey.share.data.prefs.PreferenceStorage
import com.pj109.xkorey.share.domain.UseCase
import timber.log.Timber
import javax.inject.Inject

class NumberSubtractUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : UseCase<Pair<Long,MutableLiveData<Long>>,Unit>() {

    override fun execute(parameters: Pair<Long,MutableLiveData<Long>>) {
        var (sub,cd) = parameters
        while (sub>0){
            sub-=1
            cd.postValue(sub)
            Thread.sleep(1000)
            Timber.i("number value ${sub}")
        }
    }

}