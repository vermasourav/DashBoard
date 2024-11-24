/*
 * Created by: V3RMA SOURAV on 07/03/24, 11:53 pm
 * Copyright © 2023 All rights reserved
 * Class name : DashBoardItem
 * Last modified:  07/03/24, 10:09 pm
 * Location: Bangalore, India
 */

package com.verma.android.dashboard;

import android.text.TextUtils;
import android.util.Patterns;
import android.webkit.URLUtil;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.databinding.BindingAdapter;

import com.verma.android.dashboard.pojo.Child;

import java.util.ArrayList;
import java.util.List;

public class DashBoardItem {

    @DrawableRes
    private int image = R.drawable.ic_launcher;
    private int id;
    private  String count;
    private final String name;
    private String description;
    private final String url;
    private List<Child> childs;

    public boolean isCountDisplay(String text, boolean isAllow){
        if(isAllow){
            return !TextUtils.isEmpty(text) && !text.equals("0");
        }else{
            return false;
        }
    }
    public boolean isDescriptiontDisplay(String text, boolean isAllow) {
        if(isAllow){
            return !TextUtils.isEmpty(text);
        }else{
            return false;
        }
    }



    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DashBoardItem{");
        sb.append("image=").append(image);
        sb.append(", id=").append(id);
        sb.append(", count='").append(count).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", description='").append(description).append('\'');
        if(null == childs){
            sb.append(", childs=").append("NULL");
        }else{
            sb.append(", childs=").append(childs);
        }
        sb.append('}');
        return sb.toString();
    }

    private DashBoardItem(DashBoardItemBuilder builder) {
        this.name = builder.name;
        this.image = builder.image;
        this.url = builder.url;
        this.childs = builder.childs;
        this.id = builder.id;
        this.count = builder.count;
        this.description = builder.description;
    }

    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public int getImage() {
        return image;
    }
    public int getId() {
        return id;
    }

    public String getCount() {
        return count;
    }

    public List<Child> getChilds() {
        return childs;
    }

    public static class DashBoardItemBuilder {

        private int id;
        private String count;
        private String url;
        private String name;
        private String description;
        private int image;
        private boolean visible;
        private List<Child> childs= new ArrayList<>();

        public DashBoardItemBuilder() {
            //DO Nothing
        }

        public DashBoardItemBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public DashBoardItemBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public DashBoardItemBuilder setURL(String url) {
            this.url = url;
            return this;
        }

        public DashBoardItemBuilder setImage(@DrawableRes int image) {
            this.image = image;
            return this;
        }
        public DashBoardItemBuilder setChilds(List<Child> childs) {
            this.childs = childs;
            return this;
        }
        public DashBoardItemBuilder setVisible(boolean visible) {
            this.visible = visible;
            return this;
        }
        public DashBoardItemBuilder setId(int id) {
            this.id = id;
            return this;
        }
        public DashBoardItemBuilder setCount(String count) {
            this.count = count;
            return this;
        }
        public DashBoardItem build() {
            setupLocalImage();
            return new DashBoardItem(this);
        }

        private void setupLocalImage() {
            if(!TextUtils.isEmpty(url) && !isValidURL(url)){
                image = R.drawable.no_image;
            }

        }

        private boolean isValidURL(String url){
            return !TextUtils.isEmpty(url) && URLUtil.isValidUrl(url) && Patterns.WEB_URL.matcher(url).matches();
        }
    }
}