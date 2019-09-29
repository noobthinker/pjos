package com.pj109.xkorey.model.enums

enum class MediaType(val tag:String, val desc:String) {
    MediaImage("image","image"),
    MediaVideo("video","video"),
    Tag("tag","video"),
    Maze("maze","video"),
    Default("unknow","unknow");

    companion object{
        fun target(tag:String):MediaType{
            values().forEach {
                if(it.tag.equals(tag)){
                    return it
                }
            }
            return Default
        }
    }

}