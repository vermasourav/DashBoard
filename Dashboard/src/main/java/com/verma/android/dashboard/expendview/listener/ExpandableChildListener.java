/*
 * Created by: V3RMA SOURAV on 07/03/24, 11:53 pm
 * Copyright © 2023 All rights reserved
 * Class name : ExpandableChildListener
 * Last modified:  07/03/24, 10:09 pm
 * Location: Bangalore, India
 */

package com.verma.android.dashboard.expendview.listener;

import com.verma.android.dashboard.DashBoardItem;
import com.verma.android.dashboard.pojo.Child;

public interface ExpandableChildListener {
    void onChildClick(Child child, int groupPos, int childPos, DashBoardItem group);
}