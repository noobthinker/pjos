package com.pj109.xkorey.share.domain.http

import com.pj109.xkorey.model.data.Request
import com.pj109.xkorey.model.data.http.RegisterResponse
import com.pj109.xkorey.share.data.HttpDataDownloader
import com.pj109.xkorey.share.domain.UseCase
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val dataSource: HttpDataDownloader
):UseCase<Request, RegisterResponse>() {

    override fun execute(parameters: Request): RegisterResponse {
        return dataSource.load(parameters,RegisterResponse::class.java)
    }
}