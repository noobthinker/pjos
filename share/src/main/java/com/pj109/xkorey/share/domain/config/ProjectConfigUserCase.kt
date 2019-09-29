package com.pj109.xkorey.share.domain.config

import com.pj109.xkorey.model.data.ConfResponse
import com.pj109.xkorey.model.data.Request
import com.pj109.xkorey.model.data.http.AppConfig
import com.pj109.xkorey.share.data.HttpDataDownloader
import com.pj109.xkorey.share.domain.UseCase
import timber.log.Timber
import javax.inject.Inject

class ProjectConfigUserCase @Inject constructor(
    private val dataSource: HttpDataDownloader
):UseCase<Request, ConfResponse>() {

    override fun execute(parameters: Request): ConfResponse {
        val it =  dataSource.load(parameters,ConfResponse::class.java)
        return it
    }
}