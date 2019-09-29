package com.pj109.xkorey.model.enums

enum class RoomEvent(eventName:String) {

    saveImage("saveImage"),
    saveVideo("saveVideo"),
    saveTag("saveTag"),
    saveMaze("saveMaze"),
    UserMessage("user-message"),
    ConfigProp("config-message"),
    HeartBeat("heart-beat"),
    Unkonw("default");

    companion object{
        fun getEvent(name:String): RoomEvent? {
            return values().filter {
                it.name.equals(name)
            }.firstOrNull()
        }
    }


}