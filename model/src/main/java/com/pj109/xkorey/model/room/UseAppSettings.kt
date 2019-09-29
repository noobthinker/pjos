package com.pj109.xkorey.model.room

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(indices = arrayOf(
    Index(value = ["configKey", "configType"],
    unique = true)
))
data class UseAppSettings (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val configKey:String?,
    val configVal:String?,
    val configType:String?,
    val createDate: Date?=Date()
)