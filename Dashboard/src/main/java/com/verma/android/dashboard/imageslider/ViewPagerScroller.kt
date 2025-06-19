package com.verma.android.dashboard.imageslider

import android.content.Context
import android.view.animation.Interpolator
import android.widget.Scroller


/*
 * Created by: V3RMA SOURAV on 17/06/25, 11:53 pm
 * Copyright © 2023 All rights reserved
 * Class name : ViewPagerAdapter
 * Last modified:  17/06/25, 10:09 pm
 * Location: Bangalore, India
 */

class ViewPagerScroller : Scroller {

    var fixedDuration = 1000 //time to scroll in milliseconds

    constructor(context: Context) : super(context)

    constructor(context: Context, interpolator: Interpolator) : super(context, interpolator)

    constructor(context: Context, interpolator: Interpolator, flywheel: Boolean) : super(context, interpolator, flywheel)


    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, fixedDuration)
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
        super.startScroll(startX, startY, dx, dy, fixedDuration)
    }
}