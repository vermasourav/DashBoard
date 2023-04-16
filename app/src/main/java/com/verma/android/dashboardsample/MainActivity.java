package com.verma.android.dashboardsample;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.verma.android.dashboard.DashBoardItem;
import com.verma.android.dashboard.DashboardClickListener;
import com.verma.android.dashboard.DashBoardManager;

import java.util.ArrayList;

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
        ArrayList<DashBoardItem> dashBoardItems = dashBoardManager.getDashBoardItems(this,"content_dashboard.json");
        dashBoardManager.setupDashboard(this,dashBoardManager.getGridLayout(includedLayout),2,dashBoardItems,dashboardClickListener);
    }

    DashboardClickListener dashboardClickListener = (v, dashBoardItem) -> {
        if(dashBoardItem.getChilds() != null){
            Timber.tag(TAG).d("onClick: %s", dashBoardItem.getChilds().toString());
        }
    };

}