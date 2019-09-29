package com.pj109.xkorey.pjos.util

import android.graphics.Color
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ViewDataBinding
import kotlin.random.Random

fun ObservableBoolean.hasSameValue(other: ObservableBoolean) = get() == other.get()

fun View.isRtl() = layoutDirection == View.LAYOUT_DIRECTION_RTL


inline fun <T : ViewDataBinding> T.executeAfter(block: T.() -> Unit) {
    block()
    executePendingBindings()
}


inline fun randomColor():Int{
    return Color.argb(255,Random.nextInt(255),Random.nextInt(255),Random.nextInt(255))
}