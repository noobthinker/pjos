package com.pj109.xkorey.pjos.ui.common

import com.pj109.xkorey.model.dto.MediaAndTags

interface EventAction {

    fun openMediaDetail(id: Int)
    fun onStarClicked(media: MediaAndTags)
}