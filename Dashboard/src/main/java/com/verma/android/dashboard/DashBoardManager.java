/*
 * Created by: V3RMA SOURAV on 07/03/24, 11:53 pm
 * Copyright © 2023 All rights reserved
 * Class name : DashBoardManager
 * Last modified:  07/03/24, 10:09 pm
 * Location: Bangalore, India
 */

package com.verma.android.dashboard;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.verma.android.dashboard.pojo.Child;
import com.verma.android.dashboard.pojo.DashBoardGroup;
import com.verma.android.dashboard.pojo.Group;
import com.verma.android.dashboard.window.WindowGroup;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class DashBoardManager {
    public static final String USER_AGENT = "DashBoardManager";
    public static final String APP_AGENT = "V3RMA";
    private static final String TAG = "DashBoardManager";
    private Gson gson;

    public Setup getSetup() {
        return setup;
    }

    public DashBoardManager setSetup(Setup setup) {
        this.setup = setup;
        return this;
    }

    public Setup setup = new Setup();

    public DashBoardManager() {
        gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return false;
                    }
                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                }).create();
    }

    public void setImage(final ImageView pImageView, int pImageResId, String pURl) {
        if (null == pImageView) {
            if (setup.isDebugLogs()) {
                Log.d(TAG, "setImage: Image View is null");
            }
        } else {
            if (pImageResId > 0) {
                pImageView.setImageResource(pImageResId);
            } else {
                setImageWithGlide(pImageView.getContext(), pURl, pImageView);
            }
        }

    }

    public static int convertCountVisibility(String pValue) {
        return pValue == null || pValue.isEmpty() || pValue.equalsIgnoreCase("0") ? View.GONE : View.VISIBLE;
    }


    @NonNull
    private GridLayout.LayoutParams getLayoutParams() {
        GridLayout.LayoutParams lParams = new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f));
        lParams.width = 0;    // Setting width to "0dp" so weight is applied instead
        return lParams;
    }


    private void setupGrid(GridLayout dashBoardGrid, int spanCount) {
        dashBoardGrid.setColumnCount(spanCount);
    }

    public void setupDashboard(Context pContext,
                               GridLayout dashBoardGrid,
                               int spanCount,
                               List<DashBoardItem> dashBoardItems,
                               DashboardClickListener dashboardClickListener) {
        if (null == dashBoardGrid) {
            return;
        }
        setupGrid(dashBoardGrid, spanCount);
        dashBoardGrid.removeAllViews();

        for (DashBoardItem item : dashBoardItems) {
            GridLayout.LayoutParams lParams = getLayoutParams();
            try {
                DashboardView dashboardView = getDashboardView(pContext, setup, dashboardClickListener, item, lParams);
                dashBoardGrid.addView(dashboardView);
            } catch (Exception e) {
                Log.d(TAG, "setupDashboard: " + e.getMessage());
            }
        }

    }

    @NonNull
    private DashboardView getDashboardView(Context pContext, Setup setup,
                                           DashboardClickListener dashboardClickListener, DashBoardItem item, GridLayout.LayoutParams lParams) {
        DashboardView dashboardView = new DashboardView(pContext);
        dashboardView.setLayoutParams(lParams);
        if (!setup.isImageDisplay()) {
            dashboardView.setUpOnlyText();
        }
        dashboardView.setDashBoardItem(item, setup, dashboardClickListener);
        return dashboardView;
    }

    private GlideUrl getGlideUrl(String pURL) {
        if (pURL == null) {
            return null;
        }
        LazyHeaders authHeaders = new LazyHeaders.Builder().setHeader("User-Agent", USER_AGENT).setHeader("APP-Agent", APP_AGENT).build();
        GlideUrl glideURL = new GlideUrl(pURL, authHeaders);
        if (setup.isDebugLogs()) {
            Log.d(TAG, "getGlideUrl: " + glideURL.toStringUrl());
        }
        return glideURL;
    }

    private void setImageWithGlide(Context context, final String pURL, final ImageView pImageView) {
        GlideUrl glideURL = getGlideUrl(pURL);
        Glide.with(context)
                .load(glideURL)
                .override(400, 400)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if (setup.isDebugLogs()) {
                            Log.d(TAG, "onLoadFailed: Error loading image");
                        }
                        pImageView.setImageResource(R.drawable.no_image);
                        pImageView.setVisibility(View.GONE);
                        return false; // important to return false so the error placeholder can be placed
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(pImageView);
    }

    public ArrayList<DashBoardItem> getDashBoardItems(Context context, String fileName) {
        return getDashBoardItems(context, fileName, false);
    }

    public ArrayList<DashBoardItem> getDashBoardItems(Context context, String fileName, boolean isSorting) {
        ArrayList<DashBoardItem> dashBoardItems = new ArrayList<>();
        try {
            DashBoardGroup dashboardGroup = gson.fromJson(loadJSONFromAsset(context, fileName), DashBoardGroup.class);
            dashBoardItems =  convertDashboardGroupToDashboardItems(isSorting, dashboardGroup);
        } catch (Exception e) {
            Log.d(TAG, "getDashBoardItems: " + e.getMessage());
            e.printStackTrace();
            //DO Nothing
        }
        return dashBoardItems;

    }

    private ArrayList<DashBoardItem>  convertDashboardGroupToDashboardItems(boolean isSorting, DashBoardGroup dashboardGroup) {
        ArrayList<DashBoardItem> dashBoardItems = new ArrayList<>();

        final List<Group> groups = dashboardGroup.getGroups();
        groups.forEach(group -> {
            List<Child> childes = group.getChilds();
            if (null == childes) {
                childes = new ArrayList<>();
            }
            if (isSorting) {
                childes.sort((o1, o2) -> o1.getChildName().compareToIgnoreCase(o2.getChildName()));
            }
            String count = "";
            count = childes.size() + "";
            if (count.equalsIgnoreCase("0")) {
                count = "";
            }

            if (group.isVisible()) {
                DashBoardItem item =
                        new DashBoardItem.DashBoardItemBuilder()
                                .setURL(group.getImageUrl())
                                .setName(group.getName())
                                .setVisible(group.isVisible())
                                .setChilds(childes)
                                .setCount(count)
                                .setDescription(group.getDescription())
                                .build();
                dashBoardItems.add(item);
            }

        });
        if (isSorting) {
            dashBoardItems.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
        }
        return dashBoardItems;
    }


    public ArrayList<DashBoardWindowItem> getWindowsItems(Context context, String fileName) {
        ArrayList<DashBoardWindowItem> dashBoardWindowItems = new ArrayList<>();
        try {
            WindowGroup windowGroup = gson.fromJson(loadJSONFromAsset(context, fileName), WindowGroup.class);
            ArrayList<DashBoardGroup>   dashBoardGroups = (ArrayList<DashBoardGroup>) windowGroup.getDashBoardRow();
            dashBoardGroups.forEach((dashBoardGroup) -> {
                DashBoardWindowItem dashBoardWindowItem = new DashBoardWindowItem();
                ArrayList<DashBoardItem> dashBoardItems = convertDashboardGroupToDashboardItems(false, dashBoardGroup);
                dashBoardWindowItem.setDashBoardItems(dashBoardItems);
                dashBoardWindowItems.add(dashBoardWindowItem);
            });

        } catch (Exception e) {
            Log.e(TAG, "Error parsing window items from " + fileName, e);
            //DO Nothing
        }
        return dashBoardWindowItems;

    }


    private String loadJSONFromAsset(Context context, String pFileName) {
        String json;
        try (InputStream is = context.getAssets().open(pFileName)) {
            int size = is.available();
            byte[] buffer = new byte[size];
            int bytesRead = is.read(buffer);
            if (bytesRead != size) {
                Log.w(TAG, "loadJSONFromAsset: bytesRead != size for " + pFileName);
            }
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            Log.e(TAG, "Error loading JSON from asset: " + pFileName, ex);
            return null;
        }
        return json;
    }

    public GridLayout getGridLayout(View includedLayout) {
        return (GridLayout) includedLayout.findViewById(com.verma.android.dashboard.R.id.child_board_grid);
    }

}
