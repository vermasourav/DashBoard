package com.verma.android.dashboardsample;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.verma.android.dashboard.DashBoardItem;
import com.verma.android.dashboard.DashboardClickListener;
import com.verma.android.dashboard.DashBoardManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    private  final String TAG = getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupDashboard();
    }


    public void setupDashboard() {
        DashBoardManager dashBoardManager = new DashBoardManager();

        View includedLayout =  findViewById(R.id.dashboard);
       // ArrayList<DashBoardItem> dashBoardItems = dashBoardManager.getDashBoardItems(this,"content_dashboard.json", true);
        ArrayList<DashBoardItem> dashBoardItems = dashBoardManager.getDashBoardItems(this,"content_dashboard.json");
        Collections.sort(dashBoardItems, Comparator.comparing(o -> o.getName().toLowerCase()));
        dashBoardManager.setupDashboard(this,dashBoardManager.getGridLayout(includedLayout),2,dashBoardItems,dashboardClickListener);
    }

    
    DashboardClickListener dashboardClickListener = (v, dashBoardItem) -> {
        if(dashBoardItem.getChilds() != null){
            Toast.makeText(this,dashBoardItem.getName(),Toast.LENGTH_LONG).show();
            Timber.tag(TAG).d("onClick: %s", dashBoardItem.getChilds().toString());
        }
    };

}