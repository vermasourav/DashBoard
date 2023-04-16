package com.verma.android.dashboard.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Child implements Serializable {
    @SerializedName("child_id")
    @Expose
    private Integer childId;
    @SerializedName("child_name")
    @Expose
    private String childName;

    @SerializedName("child_visible")
    @Expose
    private boolean visible;


    public Integer getChildId() {
        return childId;
    }

    public void setChildId(Integer childId) {
        this.childId = childId;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Child{");
        sb.append("childId=").append(childId);
        sb.append(", childName='").append(childName).append('\'');
        sb.append(", child_visible='").append(visible).append('\'');
        sb.append('}');
        sb.append('\n');
        return sb.toString();
    }
}