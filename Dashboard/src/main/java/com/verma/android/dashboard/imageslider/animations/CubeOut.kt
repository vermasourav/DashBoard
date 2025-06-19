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

class CubeOut: PageTransformer {
    override fun transformPage(view: View, position: Float) {
        view.pivotX = if (position < 0f) view.width.toFloat() else 0f
        view.pivotY = view.height * 0.5f
        view.rotationY = 90f * position
    }
}