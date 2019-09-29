package com.pj109.xkorey.pjos.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.pj109.xkorey.pjos.BuildConfig
import com.pj109.xkorey.pjos.R


class WelcomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_welcome, container, false)
        view.findViewById<LottieAnimationView>(R.id.logo).apply {
            val interpolator =
                AnimationUtils.loadInterpolator(context, android.R.interpolator.linear_out_slow_in)
            alpha = 0.4f
            scaleX = 0.8f
            scaleY = 0.8f
            doOnLayout {
                translationY = height / 3f
                animate()
                    .alpha(1f)
                    .translationY(0f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .interpolator = interpolator
                playAnimation()
            }
        }
//        val welcome = view.findViewWithTag<TextView>(R.id.welcome)
        val selfname = view.findViewById<TextView>(R.id.selftname)
        selfname.text=BuildConfig.APPNAME
        return view
    }


}
