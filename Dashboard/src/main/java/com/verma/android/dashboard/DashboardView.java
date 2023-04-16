package com.verma.android.dashboard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.verma.android.dashboard.databinding.DashboardItemBinding;

import timber.log.Timber;


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

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // binding = MyCustomBinding.inflate(inflater, this, true);


        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dashboard_item, this, true);
    }

    private void populateCustomValue(AttributeSet attrs) {
        if (null == attrs || null != context) {
            Timber.tag(TAG).d("populateCustomValue: ");
        }
    }

    public void setDashBoardItem(DashBoardItem dashBoardItem, DashboardClickListener dashboardViewClick) {
        try{
            binding.setItem(dashBoardItem);
            new DashBoardManager().setImage(binding.cardImage,dashBoardItem.getImage(),dashBoardItem.getUrl());
            binding.dashboardCard.setOnClickListener(view -> {
                if(null != dashboardViewClick){
                    dashboardViewClick.onClick(binding.dashboardCard,dashBoardItem);
                }else{
                    Timber.tag(TAG).d("dashboardViewClick is Null ");

                }
            });
        }catch (Exception e){
            //DO Nothing
        }
    }

    public void setUpOnlyText() {

        binding.dashboardCard.setMinimumHeight(250);
        //binding.dashboardCard.setMinimumWidth(100);
    }
}
