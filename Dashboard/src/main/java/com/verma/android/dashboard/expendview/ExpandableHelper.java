/*
 * Created by: V3RMA SOURAV on 07/03/24, 11:53 pm
 * Copyright © 2023 All rights reserved
 * Class name : ExpandableHelper
 * Last modified:  07/03/24, 10:09 pm
 * Location: Bangalore, India
 */

package com.verma.android.dashboard.expendview;

import android.content.Context;
import android.widget.ExpandableListView;

import com.verma.android.dashboard.DashBoardItem;
import com.verma.android.dashboard.DashBoardWindowItem;
import com.verma.android.dashboard.expendview.listener.ExpandableChildListener;
import com.verma.android.dashboard.expendview.listener.ExpandableGroupListener;
import com.verma.android.dashboard.expendview.listener.ExpandableListener;
import com.verma.android.dashboard.pojo.Child;

import java.util.ArrayList;
import java.util.List;

public class ExpandableHelper {
    public  ExpandableListAdapter adapter;
    private List<DashBoardItem> listGroup = null;
    private ExpandableListener listener;
    private ExpandableGroupListener groupListener;
    private ExpandableChildListener childClickListener;
    private CustomExpandableListView expandableListview;
    private Context context;
    private boolean sorting = false;


    public enum ChildType {
        LISTMODE,
        DASHBOARD,
        WINDOW,
        SLIDE
    }


    public ExpandableHelper() {
        //DO Nothing
    }

    public ExpandableHelper(Context context, CustomExpandableListView expandableListview) {
        init(context, expandableListview, null, sorting);
    }

    private ExpandableHelper(Context context, CustomExpandableListView expandableListview, List<DashBoardItem> groups, boolean sorting) {
        init(context, expandableListview, groups, sorting);
    }

    public static List<DashBoardItem> getSampleGroupList(int headerCount) {
        return new SampleDataCreator().getGroupList(headerCount, null);
    }
    public static List<DashBoardWindowItem> getSampleWindowList(int headerCount) {
        return new SampleDataCreator().getSampleWindowList(headerCount);
    }

    public static List<DashBoardItem> getSampleGroupList(Boolean asListView) {
        SampleDataCreator sample = new SampleDataCreator();
        return sample.getGroupList(5, asListView);
    }

    public ExpandableHelper build() {
        return new ExpandableHelper(context, expandableListview, listGroup, sorting);
    }

    public ExpandableHelper withList(List<DashBoardItem> pList, boolean sorting) {
        init(this.context, this.expandableListview, pList, sorting);
        return this;
    }

    public void isWithImage(boolean withImage) {
        adapter.isWithImage(withImage);
    }

    public void isWithSorting(boolean sorting) {
        adapter.isWithSorting(sorting);
    }

    public void withChildMode(int childType) {
        adapter.withChildMode(childType);
    }
    public void withChildModeCount(int childModeColumnCount) {
        adapter.withChildModeCount(childModeColumnCount);
    }


    private void init(Context context, CustomExpandableListView expandableListview, List<DashBoardItem> pList, boolean sorting) {
        this.expandableListview = expandableListview;
        this.context = context;
        this.sorting = sorting;
        doUpdate(pList);
        adapter = new ExpandableListAdapter(context, listGroup);
        expandableListview.setAdapter(adapter);
        setListener();
    }

    public void setClickListener(ExpandableListener listener) {
        this.listener = listener;
    }

    public void setGroupClickListener(ExpandableGroupListener groupListener) {
        this.groupListener = groupListener;
    }

    public void setChildClickListener(ExpandableChildListener childClickListener) {
        this.childClickListener = childClickListener;
    }

    void setListener() {
        expandableListview.setGroupIndicator(null);
        expandableListview.setOnGroupClickListener((expandableListView, view, groupPos, l) -> {
            DashBoardItem currentGroup = adapter.getGroup(groupPos);
            if (null != listener) {
                listener.onGroupClick(currentGroup, groupPos);
            }
            if (null != groupListener) {
                groupListener.onGroupClick(currentGroup, groupPos);
            }
            return false;
        });


        // This listener will expand one group at one time
        // You can remove this listener for expanding all groups
        expandableListview.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            // Default position
            int previousGroup = -1;
            @Override
            public void onGroupExpand(int groupPosition) {
                // Collapse the previously expanded group
                if (groupPosition != previousGroup && previousGroup != -1) {
                    expandableListview.collapseGroup(previousGroup);
                }
                previousGroup = groupPosition;
            }
        });
        // This listener will show toast on child click

        expandableListview.setOnChildClickListener((expandableListView, view, groupPos, childPos, l) -> {
            DashBoardItem header = adapter.getGroup(groupPos);
            Child child = adapter.getChild(groupPos, childPos);

            if (null != listener) {
                listener.onChildClick(child, groupPos, childPos, header);
            }

            if (null != childClickListener) {
                childClickListener.onChildClick(child, groupPos, childPos, header);
            }

            return false;
        });
    }
    public void doUpdate(List<DashBoardItem> listDataHeader) {
        if (null == listDataHeader) {
            this.listGroup = new ArrayList<>();
        } else {
            this.listGroup = new ArrayList<>(listDataHeader);
        }
        if (null != adapter) {
            adapter.doUpdate(this.listGroup, sorting);
        }
    }

    public List<DashBoardItem> getGroups() {
        return this.listGroup;
    }

    public void doUpdateWithSample() {
        doUpdate(ExpandableHelper.getSampleGroupList(25));
    }

    public void doUpdate(ArrayList<DashBoardItem> dashBoardItems) {

        if (null == dashBoardItems) {
            this.listGroup = new ArrayList<>();
        } else {
            this.listGroup = new ArrayList<>(dashBoardItems);
        }
        if (null != adapter) {
            adapter.doUpdate(this.listGroup, sorting);
        }

    }
}
