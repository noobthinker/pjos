package com.pj109.xkorey.share.domain.room

import com.pj109.xkorey.model.room.UseAppSettings
import com.pj109.xkorey.share.data.room.UseAppSettingsDao
import com.pj109.xkorey.share.domain.UseCase
import javax.inject.Inject

class ConfigSetUseCase @Inject constructor(
    private val dao: UseAppSettingsDao
) : UseCase<Triple<String,String,String>,Unit>() {

    override fun execute(parameters: Triple<String, String, String>) {
        val (type,key,value) = parameters
        val set= UseAppSettings(null,key,value,type)
        dao.insert(set)
    }
}