package com.pj109.xkorey.share.domain.room

import com.pj109.xkorey.share.data.room.MediaDao
import com.pj109.xkorey.share.domain.UseCase
import javax.inject.Inject

class ImageSaveUseCase @Inject constructor(
    private val dao: MediaDao
) : UseCase<String,Boolean>() {

    override fun execute(parameters: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}