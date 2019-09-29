package com.pj109.xkorey.share.domain.room

import com.pj109.xkorey.model.room.MediaTags
import com.pj109.xkorey.model.room.Tag
import com.pj109.xkorey.share.data.room.MediaTagsDao
import com.pj109.xkorey.share.domain.UseCase
import javax.inject.Inject

class MediaTagsUseCase @Inject constructor(
    private val tagDao: MediaTagsDao
) : UseCase<Int,List<MediaTags>>() {

    override fun execute(parameters: Int):List<MediaTags> {
        return tagDao.getAll(parameters)
    }
}