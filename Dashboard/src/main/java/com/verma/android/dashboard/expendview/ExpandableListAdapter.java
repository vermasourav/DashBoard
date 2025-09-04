/*
 * Created by: V3RMA SOURAV on 07/03/24, 11:53 pm
 * Copyright © 2023 All rights reserved
 * Class name : ExpandableListAdapter
 * Last modified:  07/03/24, 10:09 pm
 * Location: Bangalore, India
 */

package com.verma.android.dashboard.expendview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.verma.android.dashboard.DashBoardItem;
import com.verma.android.dashboard.DashBoardManager;
import com.verma.android.dashboard.DashBoardWindowItem;
import com.verma.android.dashboard.DashboardClickListener;
import com.verma.android.dashboard.R;
import com.verma.android.dashboard.Setup;
import com.verma.android.dashboard.databinding.ExpendViewHeaderBinding;
import com.verma.android.dashboard.databinding.ExpendedViewDashboardBinding;
import com.verma.android.dashboard.databinding.ExpendedViewListBinding;
import com.verma.android.dashboard.databinding.ExpendedViewWindowBinding;
import com.verma.android.dashboard.pojo.Child;
import com.verma.android.dashboard.window.WindowAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "ExpandableListAdapter";
    private final Context context;
    private boolean withSorting = false;
    private boolean withImage = true;
    private boolean withChildArrow = true;


    private int mChildType = 0;
    private int childModeCount = 2;

    private List<DashBoardItem> groups;
    Setup setup = new Setup();

    public ExpandableListAdapter(Context context, List<DashBoardItem> groups) {
        this.context = context;
        this.groups = groups;
        doSorting();

        //Do Default Setup
        Setup setup = new Setup();
        setup.setDebugLog(true);
        setup.setCountDisplay(false);
        setup.setImageDisplay(withImage);
        setup.setDescriptionDisplay(true);

        doSetup(setup);

    }

    public void doSetup(Setup setup) {
        this.setup = setup;
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
        pChildList.sort(Comparator.comparing(lhs -> {
            String childName = lhs.getChildName();
            if (childName == null) {
                return ""; // Treat null as empty string for sorting purposes
            }
            return childName.toLowerCase();
        }));
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
        ExpendedViewListBinding expendedViewListBinding = null;
        ExpendedViewDashboardBinding expendedViewDashboardBinding =null;
        ExpendedViewWindowBinding expendedViewWindowBinding = null;

        Object tag;
        convertView = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            if (ExpandableHelper.ChildType.LISTMODE.ordinal() == mChildType) {
                expendedViewListBinding = ExpendedViewListBinding.inflate(inflater, parent, false);
                convertView = expendedViewListBinding.getRoot();
                convertView.setTag(expendedViewListBinding);
            } else if (ExpandableHelper.ChildType.WINDOW.ordinal()  == mChildType) {
                expendedViewWindowBinding = ExpendedViewWindowBinding.inflate(inflater, parent, false);
                convertView = expendedViewWindowBinding.getRoot();
                convertView.setTag(expendedViewWindowBinding);
            } else if (ExpandableHelper.ChildType.DASHBOARD.ordinal() == mChildType) {
                expendedViewDashboardBinding = ExpendedViewDashboardBinding.inflate(inflater,parent,false);
                convertView = expendedViewDashboardBinding.getRoot();
                convertView.setTag(expendedViewDashboardBinding);
            }  else {
                // Handle other child types or return a default view
                convertView =  new View(context); // Placeholder
                convertView.setTag(null);
            }
        } else {
            tag = convertView.getTag();
            if (ExpandableHelper.ChildType.LISTMODE.ordinal() == mChildType && tag instanceof ExpendedViewListBinding) {
                expendedViewListBinding = (ExpendedViewListBinding) tag;
            } else if (ExpandableHelper.ChildType.WINDOW.ordinal() == mChildType && tag instanceof ExpendedViewWindowBinding) {
                expendedViewWindowBinding = (ExpendedViewWindowBinding) tag;
            } else if (ExpandableHelper.ChildType.DASHBOARD.ordinal() == mChildType && tag instanceof ExpendedViewDashboardBinding) {
                expendedViewDashboardBinding = (ExpendedViewDashboardBinding) tag;
            } else {
                //Set Default
                //LayoutInflater inflater = LayoutInflater.from(context);
               // expendedViewListBinding = ExpendedViewListBinding.inflate(inflater, parent, false);
              //  convertView = expendedViewListBinding.getRoot();
               // convertView.setTag(expendedViewListBinding);
                //childType = ExpandableHelper.ChildType.LISTMODE.ordinal();
            }
        }

        if (ExpandableHelper.ChildType.LISTMODE.ordinal() == mChildType) {
            populateListChild(child, expendedViewListBinding);
        } else if (ExpandableHelper.ChildType.WINDOW.ordinal() == mChildType) {
            populateWindowChild(groupPosition, expendedViewWindowBinding);
        } else if (ExpandableHelper.ChildType.DASHBOARD.ordinal() == mChildType) {
            populateDashboardChild(groupPosition, expendedViewDashboardBinding);
        }

        return convertView;
    }


    private void setThumbnailImage(String thumbnailString, ImageView thumbnail) {
        if (withImage) {
            thumbnail.setVisibility(View.VISIBLE);
            thumbnail.setImageResource(R.drawable.no_image);
            if (!TextUtils.isEmpty(thumbnailString)) {
                Glide.with(context)
                        .load(thumbnailString)
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

    private void populateListChild(Child child, ExpendedViewListBinding binding) {
        if (binding == null) return;
        binding.child.setText(child.getChildName());
        setThumbnailImage(child.getThumbnail(), binding.thumbnail);
        binding.nextImage.setVisibility(withChildArrow ? View.VISIBLE : View.GONE);
        if (!TextUtils.isEmpty(child.getDescription())) {
            binding.description.setVisibility(View.VISIBLE);
            binding.description.setText(child.getDescription());
        } else {
            binding.description.setVisibility(View.GONE);
        }
    }

    private void populateWindowChild(int groupPosition, ExpendedViewWindowBinding binding) {
        if (binding == null) return;

        ArrayList<DashBoardWindowItem> windowItems = getDashBoardWindowItems(groupPosition);

        WindowAdapter windowAdapter = new WindowAdapter(context, windowItems, dashboardClickListener);
        Setup setup = new Setup();
        setup.setDebugLog(false);
        setup.setCountDisplay(true);

        setup.setImageDisplay(false);
        setup.setDescriptionDisplay(true);

        windowAdapter.setSetup(setup);
        binding.windowDashboard.windowRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        binding.windowDashboard.windowRecyclerView.setAdapter(windowAdapter);

        windowAdapter.setDashboardClickListener(dashboardClickListener);
    }



    @NonNull
    private ArrayList<DashBoardWindowItem> getDashBoardWindowItems(int groupPosition) {

        int min = 2; // Inclusive lower bound
        int max = 3; // Inclusive upper bound

        ArrayList<DashBoardWindowItem> windowItems = new ArrayList<>();
        List<Child> childes = groups.get(groupPosition).getChilds();

        Random random = new Random();
        int currentIndex = 0;
        while (currentIndex < childes.size()) {
         //   int groupSize = random.nextInt(3) + 1; // Generates 1, 2, or 3
            int groupSize = random.nextInt(max - min + 1) + min;

            groupSize = Math.min(groupSize, childes.size() - currentIndex); // Ensure groupSize doesn't exceed remaining items

            DashBoardWindowItem dashBoardWindowItem = new DashBoardWindowItem();
            ArrayList<DashBoardItem> dashBoardItems = new ArrayList<>();

            for (int j = currentIndex; j < currentIndex + groupSize; j++) {
                Child child = childes.get(j);
                if (child.isVisible()) {
                    DashBoardItem.DashBoardItemBuilder dashBoardItemBuilder = new DashBoardItem.DashBoardItemBuilder()
                            .setName(child.getChildName())
                            .setVisible(child.isVisible())
                            .setDescription(child.getDescription())
                            .setId(child.getChildId())
                            .setCount("");
                    dashBoardItems.add(dashBoardItemBuilder.build());
                }
            }
            currentIndex += groupSize;

            dashBoardWindowItem.setDashBoardItems(dashBoardItems);
            windowItems.add(dashBoardWindowItem);
        }

        return windowItems;
    }

    private void populateDashboardChild(int groupPosition, ExpendedViewDashboardBinding binding) {

        if (binding == null) return;
        DashBoardManager dashBoardManager = new DashBoardManager();
        Setup setup = new Setup();
        setup.setDebugLog(false);
        setup.setCountDisplay(true);
        setup.setImageDisplay(false);
        setup.setDescriptionDisplay(true);
        dashBoardManager.setSetup(setup);

        View includedLayout = binding.getRoot().findViewById(R.id.dashboard);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth =  displayMetrics.widthPixels;
        includedLayout.getLayoutParams().width = screenWidth;

        ArrayList<DashBoardItem> dashBoardItems = getDashBoardItems(groupPosition);
        dashBoardManager.setSetup(setup);
        dashBoardManager.setupDashboard(context, dashBoardManager.getGridLayout(includedLayout), childModeCount, dashBoardItems, dashboardClickListener);
    }

    @NonNull
    private ArrayList<DashBoardItem> getDashBoardItems(int groupPosition) {
        ArrayList<DashBoardItem> dashBoardItems = new ArrayList<>();
        List<Child> childes = groups.get(groupPosition).getChilds();

        childes.forEach(child -> {
            if (child.isVisible()){
                DashBoardItem.DashBoardItemBuilder dashBoardItemBuilder =  new DashBoardItem.DashBoardItemBuilder()
                        .setName(child.getChildName())
                        .setVisible(child.isVisible())
                        .setDescription(child.getDescription())
                        .setId(child.getChildId())
                        .setCount("");
                dashBoardItems.add(dashBoardItemBuilder.build());
            }
        });
        return dashBoardItems;
    }

    DashboardClickListener dashboardClickListener = (v, dashBoardItem) -> {
        if (dashBoardItem.getChilds() != null) {
            Toast.makeText(v.getContext(), dashBoardItem.getName(), Toast.LENGTH_LONG).show();
            Log.d(TAG, ": onClick: " + dashBoardItem);
        }
    };

    @Override
    public int getChildrenCount(int groupPosition) {
        // return children count
        if (ExpandableHelper.ChildType.DASHBOARD.ordinal() == mChildType
                || ExpandableHelper.ChildType.WINDOW.ordinal() == mChildType
                || ExpandableHelper.ChildType.SLIDE.ordinal() == mChildType) {
            if (!this.groups.get(groupPosition).getChilds().isEmpty()){
                return 1;
            }
        }
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
        setThumbnailImage(dashBoardItem.getUrl(), binding.thumbnail);
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
        setup.setImageDisplay(withImage);
    }

    public void isWithSorting(boolean sorting) {
        this.withSorting = sorting;
    }

    public void isWithChildArrow(boolean withChildArrow) {
        this.withChildArrow = withChildArrow;
    }

    public void withChildMode(int childType) {
        this.mChildType = childType;
    }
    public void withChildModeCount(int childModeCount) {
        this.childModeCount = childModeCount;
    }

}