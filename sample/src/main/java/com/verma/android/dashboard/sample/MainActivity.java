/*
 * Created by: V3RMA SOURAV on 11/07/24, 10:21 pm
 * Copyright © 2024 All rights reserved
 * Class name : MainActivity
 * Last modified:  10/07/24, 2:02 am
 * Location: Bangalore, India
 */

package com.verma.android.dashboard.sample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.verma.android.dashboard.Setup;
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

public class MainActivity extends AppCompatActivity {
    private  final String TAG = getClass().getSimpleName();
    public ActivityMainBinding binding;

    String[] sampleList = {
            "Dashboard",
            "Expended List A",
            "Expended List B"
    };


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

        Setup setup    = new Setup();
        setup.setDebugLog(false);
        setup.setCountDisplay(true);
        setup.setImageDisplay(false);
        setup.setIsDiscriptionDisplay(true);
        dashBoardManager.setSetup(setup);

        View includedLayout =  findViewById(R.id.dashboard);
       // ArrayList<DashBoardItem> dashBoardItems = dashBoardManager.getDashBoardItems(this,"content_dashboard.json", true);
        ArrayList<DashBoardItem> dashBoardItems = dashBoardManager.getDashBoardItems(this,"content_dashboard.json");
        Collections.sort(dashBoardItems, Comparator.comparing(o -> o.getName().toLowerCase()));
        dashBoardManager.setupDashboard(this,dashBoardManager.getGridLayout(includedLayout),2,dashBoardItems,dashboardClickListener);
    }

    private void intExpendedList() {
        //SETUP With Code
        View dashboardView = binding.dashboard.getRoot();

        binding.textSelectedItem.setText(sampleList[0]);

        dashboardView.setVisibility(View.VISIBLE);
        binding.expandableListview.setVisibility(View.GONE);

        ArrayAdapter<String> arr;
        arr = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, sampleList);
        binding.list.setAdapter(arr);

        binding.list.setOnItemClickListener((parent, view, position, id) -> {
            binding.textSelectedItem.setText(sampleList[position]);

            if(0 == position){
                dashboardView.setVisibility(View.VISIBLE);
                binding.expandableListview.setVisibility(View.GONE);
            }else if(1 == position){
                dashboardView.setVisibility(View.GONE);
                binding.expandableListview.setVisibility(View.VISIBLE);
            }
        });

       /* binding.expandableListview.isWithImage(true);
        binding.expandableListview.isWithSorting(false);
        binding.expandableListview.isWithChildArrow(true);
        binding.expandableListview.withChildMode(0);*/


        binding.expandableListview.setGroupClickListener((group, groupPos) -> {
            binding.expandableListview.getGroups().get(groupPos);
                Log.d(TAG, "Group Clicked: You clicked : "+  group.getName());
        });

        binding.expandableListview.setChildClickListener((child, groupPos, childPos, header) -> {
            Log.d(TAG, "Child Clicked: You clicked : " +"["  +groupPos +","+childPos+"] " +  child.getChildName() );
            Log.d(TAG, "intExpendedList: ");
        });

        DashBoardManager dashBoardManager = new DashBoardManager();
        ArrayList<DashBoardItem> dashBoardItems = dashBoardManager.getDashBoardItems(this,"content_dashboard.json");
        binding.expandableListview.doUpdate(dashBoardItems);

        sample();
    }

    private void sample() {
        List<DashBoardItem> groupList = new ArrayList<>();
        List<Child> childListList = new ArrayList<>();
        childListList.add(new Child().withName("A Cancel saving black pixel perfect solid ui icon. Information record mistake. Unsuccessful process. Silhouette symbol on white space. Glyph pictogram for web, mobile. Isolated vector image - 1").withDescription("A - 1 Description").withThumbnail("https://cdn-icons-png.flaticon.com/512/566/566245.png"));
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

         binding.expandableListview.doUpdate(ExpandableHelper.getSampleGroupList(10));

        ArrayList<DashBoardItem> dashBoardItems = new DashBoardManager().getDashBoardItems(this,"content_dashboard.json");
        binding.expandableListview.doUpdate(dashBoardItems);

    }


    DashboardClickListener dashboardClickListener = (v, dashBoardItem) -> {
        if(dashBoardItem.getChilds() != null){
            Toast.makeText(this,dashBoardItem.getName(),Toast.LENGTH_LONG).show();
            Log.d(TAG, ": onClick: "+ dashBoardItem);
        }
    };

}