package com.pj109.xkorey.share.domain.http

import com.pj109.xkorey.model.data.Request
import com.pj109.xkorey.share.data.HttpDataDownloader
import com.pj109.xkorey.share.domain.UseCase
import javax.inject.Inject

class RefreshDataUseCase @Inject constructor(
    private val dataSource: HttpDataDownloader
) : UseCase<Request, Boolean>() {

    override fun execute(parameters: Request): Boolean {
        return true
    }

}