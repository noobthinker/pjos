package com.pj109.xkorey.pjos.ui.remark

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.pj109.xkorey.pjos.R

class RemarkTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle
    ): AppCompatTextView(context, attrs, defStyleAttr){

    var uri=""


    init {
        val drawable = context.getDrawable(R.drawable.asld_reservation)
        setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null)
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        @Suppress("SENSELESS_COMPARISON") // Status is null during super init
        if (uri == null) return super.onCreateDrawableState(extraSpace)
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
//        mergeDrawableStates(drawableState, R.attr.state_reservable)
        return drawableState
    }

}