package com.pj109.xkorey.model.room

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.pj109.xkorey.model.enums.MediaType
import java.util.*

@Entity(indices = arrayOf(
    Index(value = ["mediaType", "visitKey"],
        unique = true)
))
data class Media (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val visitKey: String?,
    val mediaType: String?,
    val mediaName: String?,
    val desc: String?,
    val mediaLength:Int?,
    val mediaStatus:Int?,
    val url: String?,
    val tags: String?,
    val marker: Boolean?,
    val copy:Boolean?,
    val createDate: Date?=Date()
){
    companion object{

        fun onlyType(type: MediaType):Media{
            return Media(null,null,type.tag,null,null,null,null,null,null,null,null)
        }

        fun onlyId(id:Int):Media{
            return Media(id,null,null,null,null,null,null,null,null,null,null)
        }
    }

}