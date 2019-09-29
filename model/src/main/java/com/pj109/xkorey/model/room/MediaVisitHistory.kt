package com.pj109.xkorey.model.room

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(indices = arrayOf(
    Index(value = ["mediaType", "mediaId"],
        unique = true)
))
data class MediaVisitHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val mediaType:String?,
    val mediaId: Int?,
    val createDate: Date=Date()
)