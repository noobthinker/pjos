package com.pj109.xkorey.share.domain.room

import com.pj109.xkorey.share.data.room.UseAppSettingsDao
import com.pj109.xkorey.share.domain.UseCase
import timber.log.Timber
import javax.inject.Inject

class ConfigQueryUseCase @Inject constructor(
    private val dao: UseAppSettingsDao
) : UseCase<Pair<String,String>,String?>() {

    override fun execute(parameters: Pair<String, String>): String? {
        val(type,key)=parameters
        val conf = dao.getConfig(type,key)
        if(null==conf){
            Timber.i("db query end.")
            return ""
        }
        Timber.i("db query null.")
        return conf.configVal
    }
}