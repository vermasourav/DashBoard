/*
 * Created by: V3RMA SOURAV on 07/03/24, 11:53 pm
 * Copyright © 2023 All rights reserved
 * Class name : Child
 * Last modified:  07/03/24, 10:09 pm
 * Location: Bangalore, India
 */

package com.verma.android.dashboard.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Child implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer childId;
    @SerializedName("name")
    @Expose
    private String childName;

    @SerializedName("visible")
    @Expose
    private boolean visible;

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;


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

    public Child withName(String childName) {
        setChildName(childName);
        return this;
    }

    public Child withId(int pID) {
        setChildId(pID);
        return this;
    }

    public Child withThumbnail(String thumbnail) {
        setThumbnail(thumbnail);
        return this;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getThumbnail() {
        return "";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Child withDescription(String description) {
        setDescription(description);
        return this;
    }
}