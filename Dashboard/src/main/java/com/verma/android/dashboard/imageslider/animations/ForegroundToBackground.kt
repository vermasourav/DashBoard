package com.verma.android.dashboard.imageslider.animations

import android.view.View
import androidx.viewpager.widget.ViewPager.PageTransformer
import kotlin.math.abs
import kotlin.math.min

/*
 * Created by: V3RMA SOURAV on 17/06/25, 11:53 pm
 * Copyright © 2023 All rights reserved
 * Class name : ViewPagerAdapter
 * Last modified:  17/06/25, 10:09 pm
 * Location: Bangalore, India
 */

class ForegroundToBackground: PageTransformer {

    override fun transformPage(view: View, position: Float) {
        val height: Float = view.height.toFloat()
        val width: Float = view.width.toFloat()
        val scale: Float = min(if (position > 0) 1f else abs(1f + position), 1f)

        view.scaleX = scale
        view.scaleY = scale
        view.pivotX = width * 0.5f
        view.pivotY = height * 0.5f
        view.translationX = if (position > 0) width * position else -width * position * 0.25f
    }

}