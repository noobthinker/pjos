package com.pj109.xkorey.model.data.http

import com.google.gson.annotations.SerializedName
import com.pj109.xkorey.model.data.Response
import com.pj109.xkorey.model.data.http.part._Register

class RegisterResponse:Response() {

    @SerializedName("data")
    var dota: _Register?=null
}