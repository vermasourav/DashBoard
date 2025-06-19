package com.verma.android.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.verma.android.dashboard.imageslider.ViewPagerScroller
import com.verma.android.dashboard.imageslider.adapters.ViewPagerAdapter
import com.verma.android.dashboard.imageslider.animations.BackgroundToForeground
import com.verma.android.dashboard.imageslider.animations.CubeIn
import com.verma.android.dashboard.imageslider.animations.CubeOut
import com.verma.android.dashboard.imageslider.animations.DepthSlide
import com.verma.android.dashboard.imageslider.animations.FidgetSpinner
import com.verma.android.dashboard.imageslider.animations.FlipHorizontal
import com.verma.android.dashboard.imageslider.animations.FlipVertical
import com.verma.android.dashboard.imageslider.animations.ForegroundToBackground
import com.verma.android.dashboard.imageslider.animations.Gate
import com.verma.android.dashboard.imageslider.animations.RotateDown
import com.verma.android.dashboard.imageslider.animations.RotateUp
import com.verma.android.dashboard.imageslider.animations.Toss
import com.verma.android.dashboard.imageslider.animations.ZoomIn
import com.verma.android.dashboard.imageslider.animations.ZoomOut
import com.verma.android.dashboard.imageslider.constants.AnimationTypes
import com.verma.android.dashboard.imageslider.constants.ScaleTypes
import com.verma.android.dashboard.imageslider.interfaces.ItemChangeListener
import com.verma.android.dashboard.imageslider.interfaces.ItemClickListener
import com.verma.android.dashboard.imageslider.interfaces.ItemTouchListener
import com.verma.android.dashboard.imageslider.models.SlideModel
import java.lang.reflect.Field
import java.util.Timer
import java.util.TimerTask

@SuppressLint("ClickableViewAccessibility")
class ImageSlider @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    RelativeLayout(context, attrs, defStyleAttr) {

    private var viewPager: ViewPager? = null
    private var pagerDots: LinearLayout? = null
    private var viewPagerAdapter: ViewPagerAdapter? = null

    private var dots: Array<ImageView?>? = null

    private var currentPage = 0
    private var imageCount = 0

    private var cornerRadius: Int = 0
    private var period: Long = 0
    private var delay: Long = 0
    private var autoCycle = false

    private var selectedDot = 0
    private var unselectedDot = 0
    private var errorImage = 0
    private var placeholder = 0
    private var titleBackground = 0
    private var textAlign = "LEFT"
    private var indicatorAlign = "CENTER"
    private var swipeTimer = Timer()

    private var itemChangeListener: ItemChangeListener? = null
    private var itemTouchListener: ItemTouchListener? = null

    private var noDots = false
    private var textColor = "#FFFFFF"

    init {
        LayoutInflater.from(getContext()).inflate(R.layout.image_slider_main, this, true)
        viewPager = findViewById(R.id.view_pager)
        pagerDots = findViewById(R.id.pager_dots)

        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ImageSlider,
            defStyleAttr,
            defStyleAttr
        )

        cornerRadius = typedArray.getInt(R.styleable.ImageSlider_iss_corner_radius, 1)
        period = typedArray.getInt(R.styleable.ImageSlider_iss_period, 1000).toLong()
        delay = typedArray.getInt(R.styleable.ImageSlider_iss_delay, 1000).toLong()
        autoCycle = typedArray.getBoolean(R.styleable.ImageSlider_iss_auto_cycle, false)
        placeholder = typedArray.getResourceId(R.styleable.ImageSlider_iss_placeholder, R.drawable.image_slider_default_loading)
        errorImage = typedArray.getResourceId(R.styleable.ImageSlider_iss_error_image, R.drawable.image_slider_default_error)
        selectedDot = typedArray.getResourceId(R.styleable.ImageSlider_iss_selected_dot, R.drawable.image_slider_default_selected_dot)
        unselectedDot = typedArray.getResourceId(R.styleable.ImageSlider_iss_unselected_dot, R.drawable.image_slider_default_unselected_dot)
        titleBackground = typedArray.getResourceId(R.styleable.ImageSlider_iss_title_background, R.drawable.image_slider_default_gradient)
        noDots = typedArray.getBoolean(R.styleable.ImageSlider_iss_no_dots, false)

        if (typedArray.getString(R.styleable.ImageSlider_iss_text_align) != null){
            textAlign = typedArray.getString(R.styleable.ImageSlider_iss_text_align)!!
        }

        if (typedArray.getString(R.styleable.ImageSlider_iss_indicator_align) != null){
            indicatorAlign = typedArray.getString(R.styleable.ImageSlider_iss_indicator_align)!!
        }

        if (typedArray.getString(R.styleable.ImageSlider_iss_text_color) != null){
            textColor = typedArray.getString(R.styleable.ImageSlider_iss_text_color)!!
        }

    }

    /**
     * Set image list to adapter.
     *
     * @param  imageList  the image list by user
     */
    fun setImageList(imageList: List<SlideModel>) {
        viewPagerAdapter = ViewPagerAdapter(
            context,
            imageList,
            cornerRadius,
            errorImage,
            placeholder,
            titleBackground,
            textAlign,
            textColor
        )
        setAdapter(imageList)
    }

    /**
     * Set image list to adapter.
     *
     * @param  imageList  the image list by user
     * @param  scaleType  scale type for all image
     */
    fun setImageList(imageList: List<SlideModel>, scaleType: ScaleTypes? = null) {
        viewPagerAdapter = ViewPagerAdapter(
            context,
            imageList,
            cornerRadius,
            errorImage,
            placeholder,
            titleBackground,
            scaleType,
            textAlign,
            textColor
        )
        setAdapter(imageList)
    }

    private fun setAdapter(imageList: List<SlideModel>){
        viewPager!!.adapter = viewPagerAdapter
        imageCount = imageList.size
        if (imageList.isNotEmpty()){
            if(!noDots){
                setupDots(imageList.size)
            }
            if (autoCycle) {
                startSliding()
            }
        }
    }

    fun setSlideAnimation(animationType: AnimationTypes){
        when (animationType) {
            AnimationTypes.ZOOM_IN -> {
                viewPager!!.setPageTransformer(true, ZoomIn())
            }
            AnimationTypes.ZOOM_OUT -> {
                viewPager!!.setPageTransformer(true, ZoomOut())
            }
            AnimationTypes.DEPTH_SLIDE -> {
                viewPager!!.setPageTransformer(true, DepthSlide())
            }
            AnimationTypes.CUBE_IN -> {
                viewPager!!.setPageTransformer(true, CubeIn())
            }
            AnimationTypes.CUBE_OUT -> {
                viewPager!!.setPageTransformer(true, CubeOut())
            }
            AnimationTypes.FLIP_HORIZONTAL -> {
                viewPager!!.setPageTransformer(true, FlipHorizontal())
            }
            AnimationTypes.FLIP_VERTICAL -> {
                viewPager!!.setPageTransformer(true, FlipVertical())
            }
            AnimationTypes.ROTATE_UP -> {
                viewPager!!.setPageTransformer(true, RotateUp())
            }
            AnimationTypes.ROTATE_DOWN -> {
                viewPager!!.setPageTransformer(true, RotateDown())
            }
            AnimationTypes.FOREGROUND_TO_BACKGROUND -> {
                viewPager!!.setPageTransformer(true, ForegroundToBackground())
            }
            AnimationTypes.BACKGROUND_TO_FOREGROUND -> {
                viewPager!!.setPageTransformer(true, BackgroundToForeground())
            }
            AnimationTypes.TOSS -> {
                viewPager!!.setPageTransformer(true, Toss())
            }
            AnimationTypes.GATE -> {
                viewPager!!.setPageTransformer(true, Gate())
            }
            else -> {
                viewPager!!.setPageTransformer(true, FidgetSpinner())
            }
        }
    }

    private fun setupDots(size: Int) {
        pagerDots!!.gravity = getGravityFromAlign(indicatorAlign)
        pagerDots!!.removeAllViews()
        dots = arrayOfNulls(size)

        for (i in 0 until size) {
            dots!![i] = ImageView(context)
            dots!![i]!!.setImageDrawable(ContextCompat.getDrawable(context, unselectedDot))
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            pagerDots!!.addView(dots!![i], params)
        }
        dots!![0]!!.setImageDrawable(ContextCompat.getDrawable(context, selectedDot))

        viewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                currentPage = position
                for (dot in dots!!) {
                    dot!!.setImageDrawable(ContextCompat.getDrawable(context, unselectedDot))
                }
                dots!![position]!!.setImageDrawable(ContextCompat.getDrawable(context, selectedDot))
                if (itemChangeListener != null) itemChangeListener!!.onItemChanged(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

    }

    /**
     * Start image sliding.
     *
     * @param  changeablePeriod  optional period value
     */
    fun startSliding(changeablePeriod: Long = period) {
        stopSliding()
        scheduleTimer(changeablePeriod)
    }

    /**
     * Stop image sliding.
     *
     */
    fun stopSliding(){
        swipeTimer.cancel()
        swipeTimer.purge()
    }

    private fun scheduleTimer(period: Long) {

        viewPager!!.setViewPageScroller(ViewPagerScroller(context))

        val handler = Handler()
        val update = Runnable {
            if (currentPage == imageCount) {
                currentPage = 0
            }
            viewPager!!.setCurrentItem(currentPage++, true)
        }

        swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, delay, period)
    }

    fun ViewPager.setViewPageScroller(viewPageScroller: ViewPagerScroller) {
        try {
            val mScroller: Field = ViewPager::class.java.getDeclaredField("mScroller")
            mScroller.isAccessible = true
            mScroller.set(this, viewPageScroller)
        } catch (_: NoSuchFieldException) {
        } catch (_: IllegalArgumentException) {
        } catch (_: IllegalAccessException) {
        }

    }

    /**
     * Set item click listener for listen to image click
     *
     * @param  itemClickListener  interface callback
     */
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        viewPagerAdapter?.setItemClickListener(itemClickListener)
    }

    /**
     * Set item change listener for listen to image click
     *
     * @param  itemChangeListener  interface callback
     */
    fun setItemChangeListener(itemChangeListener: ItemChangeListener) {
        this.itemChangeListener = itemChangeListener
    }

    /**
     * Set touch listener for listen to image touch
     *
     * @param  itemTouchListener  interface callback
     */
    fun setTouchListener(itemTouchListener: ItemTouchListener) {
        this.itemTouchListener = itemTouchListener
        this.viewPagerAdapter!!.setTouchListener(itemTouchListener)
    }

    /**
     * Get layout gravity value from indicatorAlign variable
     *
     * @param  indicatorAlign  indicator align by user
     */
    fun getGravityFromAlign(textAlign: String): Int {
        return when (textAlign) {
            "RIGHT" -> {
                Gravity.RIGHT
            }
            "LEFT" -> {
                Gravity.LEFT
            }
            else -> {
                Gravity.CENTER
            }
        }
    }

}