package com.pj109.xkorey.model.data

import com.google.gson.annotations.SerializedName
import com.pj109.xkorey.model.data.http.AppConfig

class ConfResponse:Response() {
    @SerializedName("data")
    var dota: AppConfig?=null
}
