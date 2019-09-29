package com.pj109.xkorey.model.data

import com.google.gson.annotations.SerializedName

open class Response {
    @SerializedName("code",alternate=["status","responseCode"])
    var code:Int=0
    var msg: String? =null

    companion object{
        val success=200
        val error=500
    }
}