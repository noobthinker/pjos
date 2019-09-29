package com.pj109.xkorey.share.domain.room

import com.pj109.xkorey.model.room.UseAppSettings
import com.pj109.xkorey.share.BuildConfig
import com.pj109.xkorey.share.data.prefs.PreferenceStorage
import com.pj109.xkorey.share.data.room.UseAppSettingsDao
import com.pj109.xkorey.share.domain.UseCase
import com.pj109.xkorey.share.domain.config.ProjectConfigUserCase
import com.pj109.xkorey.share.result.Result
import com.pj109.xkorey.share.result.succeeded
import com.pj109.xkorey.share.util.defaultReq
import java.util.*
import javax.inject.Inject

class RoomLiveDataUseCase @Inject constructor(
   private val configDao: UseAppSettingsDao,
   private val appConfig: ProjectConfigUserCase,
   private val preferenceStorage: PreferenceStorage
) : UseCase<Any, Boolean>() {

    override fun execute(parameters: Any): Boolean {
        if(!BuildConfig.PropInitOneTime){
            preferenceStorage.onPropertiesInit=BuildConfig.PropInitOneTime
        }
        if(!preferenceStorage.onPropertiesInit){
            val it = appConfig.executeNow(defaultReq("app/properties"))
            when{
                it.succeeded ->{
                    val appConfig = (it as Result.Success).data
                    appConfig.dota!!.appConfigList!!.forEach {
                        val seting = UseAppSettings(null,it.configKey,it.configValue,it.configType, Date())
                        configDao.insert(seting)
                    }
                    preferenceStorage.onPropertiesInit=true
                }
            }
        }
        return true
    }
}