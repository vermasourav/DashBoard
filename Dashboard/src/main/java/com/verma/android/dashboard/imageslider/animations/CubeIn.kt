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

class CubeIn: PageTransformer {

    override fun transformPage(view: View, position: Float) {
        view.pivotX = if (position > 0) 0f else view.width.toFloat()
        view.pivotY = 0f
        view.rotationY = -90f * position
    }

}