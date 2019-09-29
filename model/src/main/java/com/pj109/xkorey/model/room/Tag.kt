package com.pj109.xkorey.model.room

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(indices = arrayOf(
    Index(value = ["mediaType", "tagName"],
        unique = true)
)
)
data class Tag (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val mediaId:Int?,
    val tagName: String?,
    val mediaType:String?,
    val tagStatus: Int?,
    val color:Int?,
    val fontColor:Int?,
    val createDate: Date=Date()
){
    companion object{
        fun onlyId(id:Int):Tag{
            return Tag(id,null,null,null,null,null,null)
        }

        const val Image = "image"
        const val Video = "video"

    }

    fun isUiContentEqual(other: Tag) = id == other.id && tagName == other.tagName
}