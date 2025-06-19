package com.verma.android.dashboard.imageslider.animations

import android.view.View
import androidx.viewpager.widget.ViewPager.PageTransformer

/*
 * Created by: V3RMA SOURAV on 17/06/25, 11:53 pm
 * Copyright © 2023 All rights reserved
 * Class name : ViewPagerAdapter
 * Last modified:  17/06/25, 10:09 pm
 * Location: Bangalore, India
 */

class RotateDown: PageTransformer {

    override fun transformPage(view: View, position: Float) {
        val width = view.width
        val rotation = -15f * position

        view.pivotX = width * 0.5f
        view.pivotY = 0f
        view.translationX = 0f
        view.rotation = rotation
    }

}