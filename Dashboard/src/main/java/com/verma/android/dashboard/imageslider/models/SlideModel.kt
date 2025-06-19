package com.verma.android.dashboard.imageslider.models

import com.verma.android.dashboard.imageslider.constants.ScaleTypes

/*
 * Created by: V3RMA SOURAV on 17/06/25, 11:53 pm
 * Copyright © 2023 All rights reserved
 * Class name : ViewPagerAdapter
 * Last modified:  17/06/25, 10:09 pm
 * Location: Bangalore, India
 */

class SlideModel {

    var imageUrl: String? = null
    var imagePath: Int? = 0
    var title: String? = null
    var scaleType: ScaleTypes? = null

    constructor(imageUrl: String?, title: String? = null, scaleType: ScaleTypes?  = null) {
        this.imageUrl = imageUrl
        this.title = title
        this.scaleType = scaleType
    }

    constructor(imagePath: Int?, title: String?  = null, scaleType: ScaleTypes?  = null) {
        this.imagePath = imagePath
        this.title = title
        this.scaleType = scaleType
    }

    constructor(imageUrl: String?, scaleType: ScaleTypes?) {
        this.imageUrl = imageUrl
        this.scaleType = scaleType
    }

    constructor(imagePath: Int?, scaleType: ScaleTypes?) {
        this.imagePath = imagePath
        this.scaleType = scaleType
    }

}