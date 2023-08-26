package com.verma.android.dashboard.expendview.listener;

import com.verma.android.dashboard.DashBoardItem;
import com.verma.android.dashboard.pojo.Child;
import com.verma.android.dashboard.pojo.Group;

public interface ExpandableChildListener {
    void onChildClick(Child child, int groupPos, int childPos, DashBoardItem group);
}