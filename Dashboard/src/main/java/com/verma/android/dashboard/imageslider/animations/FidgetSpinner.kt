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

class FidgetSpinner: PageTransformer {

    override fun transformPage(view: View, position: Float) {
        view.translationX = -position * view.width

        if(abs(position) < 0.5){
            view.visibility = View.VISIBLE
            view.scaleX = 1 - abs(position)
            view.scaleY = 1 - abs(position)
        } else if(abs(position) > 0.5){
            view.visibility = View.GONE
        }

        when {
            position < -1 -> {
                view.alpha = 0f
            }
            position <= 0 -> {
                view.alpha = 1f
                view.rotation = 36000 * (abs(position) * abs(position) * abs(position) * abs(position) * abs(position) * abs(position) * abs(position))
            }
            position <= 1 -> {
                view.alpha = 1f
                view.rotation = -36000 * (abs(position) * abs(position) * abs(position) * abs(position) * abs(position) * abs(position) * abs(position))
            }
            else -> {
                view.alpha = 0f
            }
        }

    }

}