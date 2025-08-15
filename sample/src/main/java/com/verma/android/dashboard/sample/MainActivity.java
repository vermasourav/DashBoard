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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.verma.android.dashboard.DashBoardWindowItem;
import com.verma.android.dashboard.ImageSlider;
import com.verma.android.dashboard.Setup;
import com.verma.android.dashboard.expendview.ExpandableHelper;
import com.verma.android.dashboard.DashBoardItem;
import com.verma.android.dashboard.DashboardClickListener;
import com.verma.android.dashboard.DashBoardManager;
import com.verma.android.dashboard.imageslider.constants.ActionTypes;
import com.verma.android.dashboard.imageslider.constants.AnimationTypes;
import com.verma.android.dashboard.imageslider.constants.ScaleTypes;
import com.verma.android.dashboard.imageslider.interfaces.ItemClickListener;
import com.verma.android.dashboard.imageslider.models.SlideModel;
import com.verma.android.dashboard.pojo.Child;
import com.verma.android.dashboard.sample.databinding.ActivityMainBinding;
import com.verma.android.dashboard.window.WindowAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    public ActivityMainBinding binding;

    private String[] sampleList = {
            "Window Dashboard",
            "Dashboard",
            "Slider",
            "Expended list listMode",
            "Expended list Window",
            "Expended list with sample data",
            "Expended list custom"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intSample();
       // setupDashboard();
        //intExpendedList(0);
        //initImageSlider();
        //initWindowDashboard();
    }

    private void intSample() {
        binding.textSelectedItem.setText(sampleList[0]);

        binding.dashboard.getRoot().setVisibility(View.VISIBLE);
        binding.expandableListview.setVisibility(View.GONE);
        binding.imageSlider.setVisibility(View.GONE);

        ArrayAdapter<String> arr;
        arr = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sampleList);
        arr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.list.setAdapter(arr);

        binding.list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                binding.textSelectedItem.setText(sampleList[position]);
                binding.dashboard.getRoot().setVisibility(View.GONE);
                binding.expandableListview.setVisibility(View.GONE);
                binding.imageSlider.setVisibility(View.GONE);
                binding.windowDashboard.getRoot().setVisibility(View.GONE);

                switch (position) {
                    case 0:
                        initWindowDashboard();
                        binding.windowDashboard.getRoot().setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        setupDashboard();
                        binding.dashboard.getRoot().setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        initImageSlider();
                        binding.imageSlider.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        intExpendedList(0);
                        binding.expandableListview.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        intExpendedList(1);
                        binding.expandableListview.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        intExpendedSample();
                        binding.expandableListview.setVisibility(View.VISIBLE);
                        break;
                    case 6:
                        intExpendedSampleCustom();
                        binding.expandableListview.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //DO Nothing
            }
        });

    }

    private void initImageSlider() {
        ImageSlider imageSlider = binding.imageSlider;
        ArrayList<SlideModel> imageList = new ArrayList<>();
        List<DashBoardItem> dashBoardItemsImage = ExpandableHelper.getSampleGroupList(15);
        for (DashBoardItem item : dashBoardItemsImage) {
            SlideModel slideModel = new SlideModel(item.getUrl(), item.getName(), ScaleTypes.CENTER_CROP);
            slideModel.setImagePath(com.verma.android.dashboard.R.drawable.no_image);
            imageList.add(slideModel);
        }

        imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP);
        imageSlider.setSlideAnimation(AnimationTypes.ZOOM_OUT);
        imageSlider.startSliding(10000);
        imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int position) {
                Log.d(TAG, "Item Selected: " + position);
            }

            @Override
            public void doubleClick(int position) {
                Log.d(TAG, "Item doubleClick: " + position);
            }
        });

        imageSlider.setItemChangeListener(position -> {
            Log.d(TAG, "Item Changed: " + position);
        });

        imageSlider.setTouchListener((touched, position) -> {
            if (touched == ActionTypes.DOWN) {
                imageSlider.stopSliding();
            } else if (touched == ActionTypes.UP) {
                imageSlider.startSliding(1000);
            }
        });
    }

    private void initWindowDashboard() {
        // List<DashBoardWindowItem> tileItems = ExpandableHelper.getSampleWindowList(50);
        ArrayList<DashBoardWindowItem> windowItems = new DashBoardManager().getWindowsItems(this, "content_dashboard_window.json");

        WindowAdapter windowAdapter = new WindowAdapter(this, windowItems, dashboardClickListener);
        Setup setup = new Setup();
        setup.setDebugLog(false);
        setup.setCountDisplay(true);
        setup.setImageDisplay(false);
        setup.setDescriptionDisplay(true);

        windowAdapter.setSetup(setup);
        binding.windowDashboard.windowRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.windowDashboard.windowRecyclerView.setAdapter(windowAdapter);

        windowAdapter.setDashboardClickListener(dashboardClickListener);

    }

    public void setupDashboard() {
        DashBoardManager dashBoardManager = new DashBoardManager();
        Setup setup = new Setup();
        setup.setDebugLog(false);
        setup.setCountDisplay(true);
        setup.setImageDisplay(false);
        setup.setDescriptionDisplay(true);
        dashBoardManager.setSetup(setup);

        View includedLayout = findViewById(R.id.dashboard);
        // ArrayList<DashBoardItem> dashBoardItems = dashBoardManager.getDashBoardItems(this,"content_dashboard.json", true);
        ArrayList<DashBoardItem> dashBoardItems = dashBoardManager.getDashBoardItems(this, "content_dashboard.json");
       // Collections.sort(dashBoardItems, Comparator.comparing(o -> o.getName().toLowerCase()));
        dashBoardManager.setupDashboard(this, dashBoardManager.getGridLayout(includedLayout), 2, dashBoardItems, dashboardClickListener);
    }

    private void intExpendedList(int childmode) {
        //SETUP With Code

       /* binding.expandableListview.isWithImage(true);
        binding.expandableListview.isWithSorting(false);
        binding.expandableListview.isWithChildArrow(true);
        binding.expandableListview.withChildMode(0);*/

        binding.expandableListview.withChildMode(childmode);
        if (childmode == 1) {
            binding.expandableListview.isWithImage(true);
        }
        binding.expandableListview.setGroupClickListener((group, groupPos) -> {
            binding.expandableListview.getGroups().get(groupPos);
            Log.d(TAG, "Group Clicked: You clicked : " + group.getName());
        });

        binding.expandableListview.setChildClickListener((child, groupPos, childPos, header) -> {
            Log.d(TAG, "Child Clicked: You clicked : " + "[" + groupPos + "," + childPos + "] " + child.getChildName());
        });

        DashBoardManager dashBoardManager = new DashBoardManager();
        ArrayList<DashBoardItem> dashBoardItems = dashBoardManager.getDashBoardItems(this, "content_dashboard.json");
        binding.expandableListview.doUpdate(dashBoardItems);

    }


    private void intExpendedSample() {
        binding.expandableListview.doUpdateWithSample();
        binding.expandableListview.doUpdate(ExpandableHelper.getSampleGroupList(5));
    }

    private void intExpendedSampleCustom() {
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
    }


    DashboardClickListener dashboardClickListener = (v, dashBoardItem) -> {
        if (dashBoardItem.getChilds() != null) {
            Toast.makeText(this, dashBoardItem.getName(), Toast.LENGTH_LONG).show();
            Log.d(TAG, ": onClick: " + dashBoardItem);
        }
    };

}