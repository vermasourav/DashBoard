package com.verma.android.dashboard.imageslider.animations

import android.view.View
import androidx.viewpager.widget.ViewPager.PageTransformer
import kotlin.math.abs

/*
 * Created by: V3RMA SOURAV on 17/06/25, 11:53 pm
 * Copyright © 2023 All rights reserved
 * Class name : ViewPagerAdapter
 * Last modified:  17/06/25, 10:09 pm
 * Location: Bangalore, India
 */

class DepthSlide: PageTransformer {
    private val MIN_SCALE = 0.75f
    override fun transformPage(view: View, position: Float) {
        val pageWidth: Int = view.width

        when {
            position < -1 -> {
                view.alpha = 0f
            }
            position <= 0 -> {
                view.alpha = 1f
                view.translationX = 0f
                view.scaleX = 1f
                view.scaleY = 1f
            }
            position <= 1 -> {
                view.alpha = 1 - position

                view.translationX = pageWidth * -position

                val scaleFactor: Float = (MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - abs(position)))
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor
            }
            else -> {
                view.alpha = 0f
            }
        }
    }
}