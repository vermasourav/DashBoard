package com.verma.android.dashboard.window;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.verma.android.dashboard.pojo.DashBoardGroup;

import java.util.List;

public class WindowGroup {
    @SerializedName("dash_board_row")
    @Expose
    private List<DashBoardGroup> dashBoardRow;

    public List<DashBoardGroup> getDashBoardRow() {
        return dashBoardRow;
    }

    public void setDashBoardRow(List<DashBoardGroup> dashBoardRow) {
        this.dashBoardRow = dashBoardRow;
    }

    public WindowGroup withDashBoardRow(List<DashBoardGroup> dashBoardRow) {
        this.dashBoardRow = dashBoardRow;
        return this;
    }

}