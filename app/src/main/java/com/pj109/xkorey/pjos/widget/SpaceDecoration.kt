package com.pj109.xkorey.pjos.widget

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.pj109.xkorey.pjos.util.isRtl

class SpaceDecoration(
    private val start: Int = 0,
    private val top: Int = 0,
    private val end: Int = 0,
    private val bottom: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val isRtl = parent.isRtl()
        outRect.set(
            if (isRtl) end else start,
            top,
            if (isRtl) start else end,
            bottom
        )
    }
}