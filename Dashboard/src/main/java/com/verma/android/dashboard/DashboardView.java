/*
 * Created by: V3RMA SOURAV on 07/03/24, 11:53 pm
 * Copyright © 2023 All rights reserved
 * Class name : DashboardView
 * Last modified:  07/03/24, 10:09 pm
 * Location: Bangalore, India
 */

package com.verma.android.dashboard;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.verma.android.dashboard.databinding.DashboardItemBinding;

public class DashboardView extends FrameLayout {

    private Context context;
    private static final String TAG = "DashboardView";
    public DashboardItemBinding binding;

    public DashboardView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public DashboardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        populateCustomValue(attrs);
    }

    public DashboardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        populateCustomValue(attrs);

    }

    private void init(Context context) {
        this.context = context;

        //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // binding = MyCustomBinding.inflate(inflater, this, true);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dashboard_item, this, true);
    }

    private void populateCustomValue(AttributeSet attrs) {
        if (null == attrs || null != context) {
            Log.d(TAG, "populateCustomValue: AttributeSet is " + (attrs == null ? "null" : "not null") + ", Context is " + (getContext() == null ? "null" : "not null"));
        }
    }

    public void setDashBoardItem(DashBoardItem dashBoardItem, Setup setup, DashboardClickListener dashboardViewClick) {
        try{

            binding.setItem(dashBoardItem);
            binding.setSetup(setup);

            if (setup.isDebugLogs()) {
                Log.d(TAG, "Setup: debugLogs " +setup.isDebugLogs()
                        +" countDisplay "+setup.isCountDisplay()
                        +" imageDisplay "+setup.isImageDisplay()
                        +" discriptionDisplay "+setup.isDiscriptionDisplay());
            }

            if( setup.isImageDisplay()){
                new DashBoardManager().setImage(binding.cardImage,dashBoardItem.getImage(),dashBoardItem.getUrl());
            }else{
                setUpOnlyText();
            }

            binding.dashboardCard.setOnClickListener(view -> {
                if(null != dashboardViewClick){
                    dashboardViewClick.onClick(binding.dashboardCard,dashBoardItem);
                }else{
                    Log.w(TAG, "setDashBoardItem: DashboardViewClick is NULL for item: " + dashBoardItem.getName());
                }
            });

        }catch (Exception e){
            Log.e(TAG, "Error in setDashBoardItem: " + e.getMessage(), e);
        }
    }

    public void setUpOnlyText() {
        binding.dashboardCard.setMinimumHeight(250);
    }
}
