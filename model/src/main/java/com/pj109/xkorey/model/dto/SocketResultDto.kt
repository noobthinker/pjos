package com.pj109.xkorey.model.dto

enum class SocketResultDto(name:String) {
    Image("image")
    {

    },
    Video("video"),
    Message("message"),
    Tag("tag"),
    Maze("maze");

    companion object{
        fun getResult(name:String): SocketResultDto? {
            return values().filter {
                it.name.equals(name)
            }.firstOrNull()
        }
    }


}