package com.pj109.xkorey.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Maze (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val mazeFrom:Int?,
    val mazeContent:String?,
    val mazeType:String?,
    val createDate: Date?= Date()
)