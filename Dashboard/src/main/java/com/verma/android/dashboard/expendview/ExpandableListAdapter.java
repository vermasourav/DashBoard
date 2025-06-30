/*
 * Created by: V3RMA SOURAV on 07/03/24, 11:53 pm
 * Copyright © 2023 All rights reserved
 * Class name : ExpandableListAdapter
 * Last modified:  07/03/24, 10:09 pm
 * Location: Bangalore, India
 */

package com.verma.android.dashboard.expendview;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.verma.android.dashboard.DashBoardItem;
import com.verma.android.dashboard.R;
import com.verma.android.dashboard.databinding.ExpendViewHeaderBinding;
import com.verma.android.dashboard.databinding.ExpendedViewChildsBinding;
import com.verma.android.dashboard.pojo.Child;

import java.util.Comparator;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "ExpandableListAdapter";
    private final Context context;
    private boolean withSorting = false;
    private boolean withImage = true;
    private boolean withChildArrow = true;

    private int childType = 0;

    private List<DashBoardItem> groups;

    public ExpandableListAdapter(Context context, List<DashBoardItem> groups) {
        this.context = context;
        this.groups = groups;
        doSorting();
    }

    public void doUpdate(List<DashBoardItem> groups, boolean sorting) {
        this.groups = groups;
        this.withSorting = sorting;
        doSorting();
        notifyDataSetChanged();
    }

    public void doSorting() {
        if (withSorting) {
            Log.d(TAG, "doSorting: ");
            groups.sort(Comparator.comparing(lhs -> lhs.getName().toLowerCase()));
            groups.forEach(header -> sortingChild(header.getChilds()));
        }
    }

    private void sortingChild(List<Child> pChildList) {
        pChildList.sort(Comparator.comparing(lhs -> lhs.getChildName().toLowerCase()));
    }

    @Override
    public Child getChild(int groupPosition, int childPosition) {
        return this.groups.get(groupPosition).getChilds().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Child child = getChild(groupPosition, childPosition);
        ExpendedViewChildsBinding binding;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            binding = ExpendedViewChildsBinding.inflate(inflater, parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (ExpendedViewChildsBinding) convertView.getTag();
        }

        if (0 == childType) {
            setChildName(child, binding.child);
            setChildImage(child, binding.thumbnail);
            setChildArrow(binding.nextImage);
            setChildDescription(child, binding.description);
        } else {
            // Handle other child types or return a default view
            // For now, returning the existing convertView or a new empty view if null
            if (convertView == null) {
                // Inflate a default empty view or handle appropriately
                // For example, return new View(context); or throw an exception
                // This part depends on how you want to handle unknown child types
                return new View(context); // Placeholder
            }
        }
        return convertView;
    }

    private static void setChildName(Child child, TextView childView) {
        childView.setText(child.getChildName());
    }

    private static void setChildDescription(Child child, TextView description) {
        if (!TextUtils.isEmpty(child.getDescription())) {
            description.setVisibility(View.VISIBLE);
            description.setText(child.getDescription());
        } else {
            description.setVisibility(View.GONE);
        }
    }

    private void setChildArrow(ImageView nextImage) {
        if (!withChildArrow) {
            nextImage.setVisibility(View.GONE);
        }
    }

    private void setChildImage(Child child, ImageView thumbnail) {
        if (withImage) {
            thumbnail.setVisibility(View.VISIBLE);
            thumbnail.setImageResource(R.drawable.no_image);
            if (!TextUtils.isEmpty(child.getThumbnail())) {
                Glide.with(context)
                        .load(child.getThumbnail())
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.no_image)
                        .into(thumbnail);
            } else {
                thumbnail.setVisibility(View.GONE);
            }
        } else {
            thumbnail.setVisibility(View.GONE);
        }
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // return children count
        return this.groups.get(groupPosition).getChilds().size();
    }

    @Override
    public DashBoardItem getGroup(int groupPosition) {
        // Get header position
        return this.groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        // Get header size
        return this.groups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        DashBoardItem dashBoardItem = getGroup(groupPosition);
        ExpendViewHeaderBinding binding;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            binding = ExpendViewHeaderBinding.inflate(inflater, parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (ExpendViewHeaderBinding) convertView.getTag();
        }

        binding.header.setText(dashBoardItem.getName());

        // If group is expanded then change the text into bold and change the icon
        if (isExpanded) {
            binding.header.setTypeface(null, Typeface.BOLD);
            binding.header.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_navigate_up, 0);
        } else {
            // If group is not expanded then change the text back into normal and change the icon
            binding.header.setTypeface(null, Typeface.NORMAL);
            binding.header.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_navigate_down, 0);
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void isWithImage(boolean withImage) {
        this.withImage = withImage;
    }
    public void isWithSorting(boolean sorting) {
        this.withSorting = sorting;
    }

    public void isWithChildArrow(boolean withChildArrow) {
        this.withChildArrow = withChildArrow;
    }

    public void withChildMode(int childType) {
        this.childType = childType;
    }
}