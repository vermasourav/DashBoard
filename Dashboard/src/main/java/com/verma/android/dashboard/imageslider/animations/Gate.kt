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

class Gate: PageTransformer {

    override fun transformPage(view: View, position: Float) {
        view.translationX = -position * view.width

        when {
            position < -1 -> {
                view.alpha = 0f
            }
            position <= 0 -> {
                view.alpha = 1f
                view.pivotX = 0f
                view.rotationY = 90 * abs(position)
            }
            position <= 1 -> {
                view.alpha = 1f
                view.pivotX = view.width.toFloat()
                view.rotationY = -90 * abs(position)
            }
            else -> {
                view.alpha = 0f
            }
        }
    }

}