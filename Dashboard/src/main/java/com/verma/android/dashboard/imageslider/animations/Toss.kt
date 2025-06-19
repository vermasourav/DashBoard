package com.verma.android.dashboard.imageslider.animations

import android.view.View
import androidx.viewpager.widget.ViewPager.PageTransformer
import kotlin.math.abs
import kotlin.math.max

/*
 * Created by: V3RMA SOURAV on 17/06/25, 11:53 pm
 * Copyright © 2023 All rights reserved
 * Class name : ViewPagerAdapter
 * Last modified:  17/06/25, 10:09 pm
 * Location: Bangalore, India
 */

class Toss: PageTransformer {

    override fun transformPage(view: View, position: Float) {
        view.translationX = -position * view.width
        view.cameraDistance = 20000f

        if(position < 0.5 && position > -0.5){
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.INVISIBLE
        }

        when {
            position < -1 -> {
                view.alpha = 0f
            }
            position <= 0 -> {
                view.alpha = 1f
                view.scaleX = max(0.4f, 1 - abs(position))
                view.scaleY = max(0.4f, 1 - abs(position))
                view.rotationX = 1080 * ( 1 - abs(position) + 1)
                view.translationY = -1000 * abs(position)
            }
            position <= 1 -> {
                view.alpha = 1f
                view.scaleX = max(0.4f, 1 - abs(position))
                view.scaleY = max(0.4f, 1 - abs(position))
                view.rotationX = -1080 * ( 1 - abs(position) + 1)
                view.translationY = -1000 * abs(position)
            }
            else -> {
                view.alpha = 0f
            }
        }
    }

}