package com.pj109.xkorey.share.data

import com.pj109.xkorey.model.data.Request
import com.pj109.xkorey.model.data.Response

interface DataDownloader {
    fun <T> load(request: Request, clazz:Class<T>): T where T: Response
}