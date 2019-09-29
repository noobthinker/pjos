package com.pj109.xkorey.model.data

import android.content.Context
import androidx.lifecycle.MutableLiveData

data class DialogVo (
    val dialogType:Int,
    val title:String,
    val message:String,
    val bttonTxt:String,
    val status:MutableLiveData<Int>,
    val context:Context
)