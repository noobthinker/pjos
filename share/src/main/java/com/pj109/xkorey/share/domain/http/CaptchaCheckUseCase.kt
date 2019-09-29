package com.pj109.xkorey.share.domain.http

import com.pj109.xkorey.model.data.Request
import com.pj109.xkorey.model.data.Response
import com.pj109.xkorey.share.data.HttpDataDownloader
import com.pj109.xkorey.share.domain.UseCase
import javax.inject.Inject

class CaptchaCheckUseCase @Inject constructor(
    private val dataSource: HttpDataDownloader
): UseCase<Request, Response>() {

    override fun execute(parameters: Request): Response {
        return dataSource.load(parameters, Response::class.java)
    }

}