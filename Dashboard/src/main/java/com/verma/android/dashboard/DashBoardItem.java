/*
 * Created by: V3RMA SOURAV on 07/03/24, 11:53 pm
 * Copyright © 2023 All rights reserved
 * Class name : DashBoardItem
 * Last modified:  07/03/24, 10:09 pm
 * Location: Bangalore, India
 */

package com.verma.android.dashboard;

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
    private final String url;
    private List<Child> childs;


    private DashBoardItem(DashBoardItemBuilder builder) {
        this.name = builder.name;
        this.image = builder.image;
        this.url = builder.url;
        this.childs = builder.childs;
        this.id = builder.id;
        this.count = builder.count;
    }

    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

    public String getName() {
        return name;
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
            return new DashBoardItem(this);
        }
    }
}