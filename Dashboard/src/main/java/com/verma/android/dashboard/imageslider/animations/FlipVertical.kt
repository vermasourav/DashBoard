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

class FlipVertical: PageTransformer {
    override fun transformPage(view: View, position: Float) {
        val rotation: Float = -180f * position

        view.alpha = if (rotation > 90f || rotation < -90f) 0f else 1f
        view.pivotX = view.width * 0.5f
        view.pivotY = view.height * 0.5f
        view.rotationX = rotation
    }
}