package com.pj109.xkorey.share.domain.room

import com.pj109.xkorey.model.room.UseAppSettings
import com.pj109.xkorey.share.data.room.UseAppSettingsDao
import com.pj109.xkorey.share.domain.UseCase
import javax.inject.Inject

class AppConfigQueryUseCase @Inject constructor(
    private val dao: UseAppSettingsDao
) :UseCase<String,List<UseAppSettings>>() {

    override fun execute(parameters: String): List<UseAppSettings> {
        return dao.getConfig(parameters)
    }

}