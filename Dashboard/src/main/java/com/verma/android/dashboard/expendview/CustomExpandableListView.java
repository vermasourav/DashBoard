/*
 * Created by: V3RMA SOURAV on 07/03/24, 11:53 pm
 * Copyright © 2023 All rights reserved
 * Class name : CustomExpandableListView
 * Last modified:  07/03/24, 10:09 pm
 * Location: Bangalore, India
 */

package com.verma.android.dashboard.expendview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

import com.verma.android.dashboard.DashBoardItem;
import com.verma.android.dashboard.R;
import com.verma.android.dashboard.expendview.listener.ExpandableChildListener;
import com.verma.android.dashboard.expendview.listener.ExpandableGroupListener;

import java.util.ArrayList;
import java.util.List;

public class CustomExpandableListView extends ExpandableListView {
    private Context context;
    private ExpandableHelper helper;

    private void init(Context context) {
        this.context = context;
        helper = new ExpandableHelper(context, this)
                .withList(null,false)
                .build();
    }
    public Context getCustomContext() {
        return context;
    }

    private void populateCustomValue(AttributeSet attrs) {
        if (null == attrs) {
            return;
        }
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomExpandableListView);
        try {
            isWithImage(typedArray.getBoolean(R.styleable.CustomExpandableListView_withImage, false));
            isWithSorting(typedArray.getBoolean(R.styleable.CustomExpandableListView_withSorting, false));
            isWithChildArrow(typedArray.getBoolean(R.styleable.CustomExpandableListView_withChildArrow, false));
            withChildMode(typedArray.getInteger(R.styleable.CustomExpandableListView_childMode, 0));
            withChildModeCount(typedArray.getInteger(R.styleable.CustomExpandableListView_childModeColumnCount, 2));

        } finally {
            typedArray.recycle();
        }
    }

    public void isWithChildArrow(boolean withChildArrow) {
        helper.adapter.isWithChildArrow(withChildArrow);
    }

    public void isWithImage(boolean withImage) {
        helper.adapter.isWithImage(withImage);
    }
    public void isWithSorting(boolean sorting) {
        helper.adapter.isWithSorting(sorting);
    }

    public void setChildClickListener(ExpandableChildListener childClickListener) {
        helper.setChildClickListener(childClickListener);
    }
    public void setGroupClickListener(ExpandableGroupListener groupClickListener) {
        helper.setGroupClickListener(groupClickListener);
    }

    public CustomExpandableListView(Context context) {
        super(context);
        init(context);
    }

    public CustomExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        populateCustomValue(attrs);
    }

    public CustomExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        populateCustomValue(attrs);
    }

    public CustomExpandableListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
        populateCustomValue(attrs); // Keep this call here
    }

    public List<DashBoardItem> getGroups() {
        return helper.getGroups();
    }

    public void doUpdate(List<DashBoardItem> groups) {
        helper.doUpdate(groups);
    }

    public void doUpdateWithSample() {
        helper.doUpdateWithSample();
    }

    public void doUpdate(ArrayList<DashBoardItem> dashBoardItems) {
        helper.doUpdate(dashBoardItems);
    }

    public void withChildMode(int childType) {
        helper.withChildMode(childType);
    }
    public void withChildModeCount(int childModeColumnCount) {
        helper.withChildModeCount(childModeColumnCount);
    }

}
