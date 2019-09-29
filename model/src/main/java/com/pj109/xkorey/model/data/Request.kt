package com.pj109.xkorey.model.data

import com.pj109.xkorey.model.enums.HttpMethod

class Request {
    var url: String?=null
    var method: HttpMethod?=HttpMethod.GET
    var header:MutableMap<String,String>?=null
    var params:MutableMap<String,Any>?=null
}