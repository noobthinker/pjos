package com.pj109.xkorey.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MediaTags(
    @PrimaryKey(autoGenerate = true)
    val mediaId:Int,
    val tagId:Int
)