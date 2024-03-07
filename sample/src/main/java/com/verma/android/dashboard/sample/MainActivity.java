/*
 * Created by: V3RMA SOURAV on 07/03/24, 11:53 pm
 * Copyright © 2023 All rights reserved
 * Class name : MainActivity
 * Last modified:  07/03/24, 11:46 pm
 * Location: Bangalore, India
 */

package com.verma.android.dashboard.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.verma.android.dashboard.expendview.ExpandableHelper;
import com.verma.android.dashboard.DashBoardItem;
import com.verma.android.dashboard.DashboardClickListener;
import com.verma.android.dashboard.DashBoardManager;
import com.verma.android.dashboard.pojo.Child;
import com.verma.android.dashboard.sample.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    private  final String TAG = getClass().getSimpleName();
    public ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intExpendedList();
        setupDashboard();
    }


    public void setupDashboard() {
        DashBoardManager dashBoardManager = new DashBoardManager();
        dashBoardManager.setCountVisiable(true);

        View includedLayout =  findViewById(R.id.dashboard);
       // ArrayList<DashBoardItem> dashBoardItems = dashBoardManager.getDashBoardItems(this,"content_dashboard.json", true);
        ArrayList<DashBoardItem> dashBoardItems = dashBoardManager.getDashBoardItems(this,"content_dashboard.json");
        Collections.sort(dashBoardItems, Comparator.comparing(o -> o.getName().toLowerCase()));
        dashBoardManager.setupDashboard(this,dashBoardManager.getGridLayout(includedLayout),2,dashBoardItems,dashboardClickListener);
    }

    private void intExpendedList() {
        //SETUP With Code
        binding.expandableListview.isWithImage(false);
        binding.expandableListview.isWithSorting(false);
        binding.expandableListview.isWithChildArrow(true);
        binding.expandableListview.withChildMode(1);

        binding.expandableListview.setGroupClickListener((group, groupPos) -> {
            binding.expandableListview.getGroups().get(groupPos);
            Timber.tag(TAG).d("You clicked : %s", group.getName());
        });

        binding.expandableListview.setChildClickListener((child, groupPos, childPos, header) -> {
            Timber.tag(TAG).d("You clicked : %s", child.getChildName());
        });

        DashBoardManager dashBoardManager = new DashBoardManager();
        ArrayList<DashBoardItem> dashBoardItems = dashBoardManager.getDashBoardItems(this,"content_dashboard.json");
        binding.expandableListview.doUpdate(dashBoardItems);

       // test();
    }

    private void test() {
        List<DashBoardItem> groupList = new ArrayList<>();
        List<Child> childListList = new ArrayList<>();
        childListList.add(new Child().withName("A - 1").withDescription("A - 1 Description"));
        childListList.add(new Child().withName("A - 2").withDescription("A - 2 Description"));
        childListList.add(new Child().withName("A - 3").withDescription("A - 3 Description"));

        DashBoardItem item =
                new DashBoardItem.DashBoardItemBuilder()
                        .setURL("")
                        .setName("A")
                        .setVisible(true)
                        .setChilds(childListList)
                        .build();
        groupList.add(item);
         binding.expandableListview.doUpdate(groupList);

         binding.expandableListview.doUpdateWithSample();

         binding.expandableListview.doUpdate(ExpandableHelper.getSampleGroupList(3));
    }


    DashboardClickListener dashboardClickListener = (v, dashBoardItem) -> {
        if(dashBoardItem.getChilds() != null){
            Toast.makeText(this,dashBoardItem.getName(),Toast.LENGTH_LONG).show();
            Timber.tag(TAG).d("onClick: %s", dashBoardItem.getChilds().toString());
        }
    };

}