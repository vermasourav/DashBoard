/*
 * Created by: V3RMA SOURAV on 24/11/24, 7:38pm
 * Copyright © 2024 All rights reserved
 * Class name : Setup
 * Last modified:  24/11/24, 7:28pm
 * Location: Bangalore, India
 */

package com.verma.android.dashboard;



public class Setup {
    public boolean debugLogs = false;
    public boolean countDisplay = true;
    public boolean imageDisplay = true;
    public boolean descriptionDisplay = false;

    public boolean isDescriptionDisplay() {
        return descriptionDisplay;
    }

    public boolean isCountDisplay() {
        return countDisplay;
    }

    public boolean isDebugLogs() {
        return debugLogs;
    }

    public boolean isImageDisplay() {
        return imageDisplay;
    }

    public void setCountDisplay(boolean countDisplay) {
        this.countDisplay = countDisplay;
    }

    public void setDebugLog(boolean isDebugLog) {
        this.debugLogs = isDebugLog;
    }

    public void setImageDisplay(boolean isImageDisplay) {
        this.imageDisplay = isImageDisplay;
    }

    public void setDescriptionDisplay(boolean descriptionDisplay) {
        this.descriptionDisplay = descriptionDisplay;
    }

}