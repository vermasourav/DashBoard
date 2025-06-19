package com.verma.android.dashboard.imageslider.adapters

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.verma.android.dashboard.R;
import com.verma.android.dashboard.imageslider.constants.ActionTypes
import com.verma.android.dashboard.imageslider.constants.ScaleTypes
import com.verma.android.dashboard.imageslider.interfaces.ItemClickListener
import com.verma.android.dashboard.imageslider.interfaces.ItemTouchListener
import com.verma.android.dashboard.imageslider.models.SlideModel
import com.verma.android.dashboard.imageslider.transformation.RoundedTransformation
import com.squareup.picasso.Picasso

/*
 * Created by: V3RMA SOURAV on 17/06/25, 11:53 pm
 * Copyright © 2023 All rights reserved
 * Class name : ViewPagerAdapter
 * Last modified:  17/06/25, 10:09 pm
 * Location: Bangalore, India
 */

class ViewPagerAdapter(context: Context?,
                       imageList: List<SlideModel>,
                       private var radius: Int,
                       private var errorImage: Int,
                       private var placeholder: Int,
                       private var titleBackground: Int,
                       private var scaleType: ScaleTypes?,
                       private var textAlign: String,
                       private var textColor: String) : PagerAdapter() {

    constructor(context: Context, imageList: List<SlideModel>, radius: Int, errorImage: Int, placeholder: Int, titleBackground: Int, textAlign: String, textColor: String) :
            this(context, imageList, radius, errorImage, placeholder, titleBackground, null, textAlign, textColor)

    private var imageList: List<SlideModel>? = imageList
    private var layoutInflater: LayoutInflater? = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?

    private var itemClickListener: ItemClickListener? = null
    private var itemTouchListener: ItemTouchListener? = null

    private var lastTouchTime: Long = 0
    private var currentTouchTime: Long = 0

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun getCount(): Int {
        return imageList!!.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View{
        val itemView = layoutInflater!!.inflate(R.layout.image_slider_pager_row, container, false)

        val imageView = itemView.findViewById<ImageView>(R.id.image_view)
        val linearLayout = itemView.findViewById<LinearLayout>(R.id.linear_layout)
        val textView = itemView.findViewById<TextView>(R.id.text_view)
        textView.setTextColor(Color.parseColor(textColor))

        if (imageList!![position].title != null){
            textView.text = imageList!![position].title
            linearLayout.setBackgroundResource(titleBackground)
            textView.gravity = getGravityFromAlign(textAlign)
            linearLayout.gravity = getGravityFromAlign(textAlign)
        }else{
            linearLayout.visibility = View.INVISIBLE
        }

        // Image from url or local path check.
        var slideModel = imageList!![position];
        val loader = if (TextUtils.isEmpty(slideModel.imageUrl)){
            if (slideModel.imagePath == 0){
                Picasso.get().load(R.drawable.image_slider_default_error)
            }else{
                Picasso.get().load(slideModel!!.imagePath!!)
            }
        }else{
            Picasso.get().load(slideModel!!.imageUrl!!)
        }

        // set Picasso options.
        if ((scaleType != null && scaleType == ScaleTypes.CENTER_CROP) || imageList!![position].scaleType == ScaleTypes.CENTER_CROP){
            loader.fit().centerCrop()
        } else if((scaleType != null && scaleType == ScaleTypes.CENTER_INSIDE) || imageList!![position].scaleType == ScaleTypes.CENTER_INSIDE){
            loader.fit().centerInside()
        }else if((scaleType != null && scaleType == ScaleTypes.FIT) || imageList!![position].scaleType == ScaleTypes.FIT){
            loader.fit()
        }

        loader.transform(RoundedTransformation(radius, 0))
            .placeholder(placeholder)
            .error(errorImage)
            .into(imageView)

        container.addView(itemView)

        imageView.setOnClickListener {
            lastTouchTime = currentTouchTime;
            currentTouchTime = System.currentTimeMillis();
            when {
                currentTouchTime - lastTouchTime < 250 -> {
                    itemClickListener?.doubleClick(position)
                }
                else -> {
                    itemClickListener?.onItemSelected(position)
                }
            }
        }

        if (itemTouchListener != null){
            imageView!!.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_MOVE -> itemTouchListener!!.onTouched(ActionTypes.MOVE, position)
                    MotionEvent.ACTION_DOWN -> itemTouchListener!!.onTouched(ActionTypes.DOWN, position)
                    MotionEvent.ACTION_UP -> itemTouchListener!!.onTouched(ActionTypes.UP, position)
                }
                false
            }
        }

        return itemView
    }

    /**
     * Get layout gravity value from textAlign variable
     *
     * @param  textAlign  text align by user
     */
    fun getGravityFromAlign(textAlign: String): Int {
        return when (textAlign) {
            "RIGHT" -> {
                Gravity.RIGHT
            }
            "CENTER" -> {
                Gravity.CENTER
            }
            else -> {
                Gravity.LEFT
            }
        }
    }

    /**
     * Set item click listener
     *
     * @param  itemClickListener  callback by user
     */
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    /**
     * Set touch listener for listen to image touch
     *
     * @param  itemTouchListener  interface callback
     */
    fun setTouchListener(itemTouchListener: ItemTouchListener) {
        this.itemTouchListener = itemTouchListener
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }

}