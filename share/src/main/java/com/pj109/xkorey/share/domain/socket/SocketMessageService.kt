package com.pj109.xkorey.share.domain.socket

import com.pj109.xkorey.share.data.NettyAllInOne

interface SocketMessageService {

    fun onMessageReceived(message:NettyAllInOne.AllInone)
}