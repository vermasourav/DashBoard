/*
 * Created by: V3RMA SOURAV on 07/03/24, 11:53 pm
 * Copyright © 2023 All rights reserved
 * Class name : DashBoardItem
 * Last modified:  07/03/24, 10:09 pm
 * Location: Bangalore, India
 */

package com.verma.android.dashboard;


import java.util.ArrayList;
import java.util.List;

public class DashBoardWindowItem {
    public List<DashBoardItem> dashBoardItems = new ArrayList<>();

    public void setDashBoardItems(List<DashBoardItem> dashBoardItems) {
        this.dashBoardItems = dashBoardItems;
    }
}