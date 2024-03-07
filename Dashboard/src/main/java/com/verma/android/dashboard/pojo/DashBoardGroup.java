/*
 * Created by: V3RMA SOURAV on 07/03/24, 11:53 pm
 * Copyright © 2023 All rights reserved
 * Class name : DashBoardGroup
 * Last modified:  07/03/24, 10:09 pm
 * Location: Bangalore, India
 */

package com.verma.android.dashboard.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashBoardGroup {

@SerializedName("groups")
@Expose
private List<Group> groups;

@SerializedName("display_count")
@Expose
private boolean countDisplay;

public List<Group> getGroups() {
return groups;
}

public void setGroups(List<Group> groups) {
this.groups = groups;
}

    public boolean isCountDisplay() {
        return countDisplay;
    }

    public DashBoardGroup setCountDisplay(boolean countDisplay) {
        countDisplay = countDisplay;
        return this;
    }

}