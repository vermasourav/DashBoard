package com.verma.android.dashboard.imageslider.interfaces
/*
 * Created by: V3RMA SOURAV on 17/06/25, 11:53 pm
 * Copyright © 2023 All rights reserved
 * Class name : ViewPagerAdapter
 * Last modified:  17/06/25, 10:09 pm
 * Location: Bangalore, India
 */

interface ItemClickListener {
    /**
     * Click listener selected item function.
     *
     * @param  position  selected item position
     */
    fun onItemSelected(position: Int)

    /**
     * Click listener double click item function.
     *
     * @param  position  selected item position
     */
    fun doubleClick(position: Int)
}