package com.verma.android.dashboard.imageslider.interfaces
/*
 * Created by: V3RMA SOURAV on 17/06/25, 11:53 pm
 * Copyright © 2023 All rights reserved
 * Class name : ViewPagerAdapter
 * Last modified:  17/06/25, 10:09 pm
 * Location: Bangalore, India
 */

interface ItemChangeListener {
    /**
     * Click listener changed item function.
     *
     * @param  position  changed item position
     */
    fun onItemChanged(position: Int)
}