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
import android.text.TextUtils;
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

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import timber.log.Timber;


public class DashBoardManager {
    public static final String USER_AGENT = "DashBoardManager";
    public static final String APP_AGENT = "V3RMA";
    private static final String TAG = "DashBoardManager";

    public void setImage(final ImageView pImageView, int pImageResId, String pURl) {
        if (!TextUtils.isEmpty(pURl)) {
            setImageWithGlide(pImageView.getContext(), pURl, pImageView);
        } else {
            if (pImageView != null && pImageResId > 0) {
                pImageView.setImageResource(pImageResId);
            }
        }
    }

    public static int convertCountVisibility(String pValue) {
        return pValue == null || pValue.isEmpty() || pValue.equalsIgnoreCase("0")? View.GONE : View.VISIBLE;
    }


    @NonNull
    private GridLayout.LayoutParams getLayoutParams() {
        GridLayout.LayoutParams lParams   = new GridLayout.LayoutParams( GridLayout.spec( GridLayout.UNDEFINED, 1f), GridLayout.spec( GridLayout.UNDEFINED, 1f));
        lParams.width = 0;    // Setting width to "0dp" so weight is applied instead
        return lParams;
    }


    private void setupGrid(GridLayout dashBoardGrid , int spanCount) {
        dashBoardGrid.setColumnCount(spanCount);
    }
    public void setupDashboard(
            boolean textOnly,
            Context pContext,
            GridLayout dashBoardGrid,
            int spanCount, List<DashBoardItem> dashBoardItems,
            DashboardClickListener dashboardClickListener ) {
        if(null == dashBoardGrid ){
            return;
        }
        setupGrid(dashBoardGrid, spanCount);
        dashBoardGrid.removeAllViews();

        for( DashBoardItem item : dashBoardItems ) {
            GridLayout.LayoutParams lParams = getLayoutParams();
            try{
                DashboardView dashboardView = getDashboardView(textOnly,pContext, dashboardClickListener, item, lParams);
                dashBoardGrid.addView(dashboardView );
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
    public void setupDashboard(Context pContext,
                               GridLayout dashBoardGrid,
                               int spanCount,
                               List<DashBoardItem> dashBoardItems,
                               DashboardClickListener dashboardClickListener
    ) {
        if(null == dashBoardGrid ){
            return;
        }
        setupGrid(dashBoardGrid, spanCount);
        dashBoardGrid.removeAllViews();

        for( DashBoardItem item : dashBoardItems ) {
            GridLayout.LayoutParams lParams = getLayoutParams();
            try{
                DashboardView dashboardView = getDashboardView(false,pContext, dashboardClickListener, item, lParams);
                dashBoardGrid.addView(dashboardView );
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }


    @NonNull
    private DashboardView getDashboardView(boolean textOnly, Context pContext, DashboardClickListener dashboardClickListener, DashBoardItem item, GridLayout.LayoutParams lParams) {
        DashboardView dashboardView = new DashboardView(pContext);
        dashboardView.setLayoutParams(lParams);
        if(textOnly){
            dashboardView.setUpOnlyText();
        }
        dashboardView.setDashBoardItem(item, dashboardClickListener);
        return dashboardView;
    }

    private GlideUrl getGlideUrl(String pURL) {
        if (pURL == null) {
            return null;
        }
        LazyHeaders authHeaders = new LazyHeaders.Builder().setHeader("User-Agent", USER_AGENT).setHeader("APP-Agent", APP_AGENT).build();
        GlideUrl glideURL = new GlideUrl(pURL, authHeaders);
        Timber.tag(TAG).d("getGlideUrl: %s", glideURL.toStringUrl());
        return glideURL;
    }

    private void setImageWithGlide(Context context, final String pURL, final ImageView pImageView) {
        GlideUrl glideURL = getGlideUrl(pURL);
        Glide.with(context)
                .load(glideURL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Timber.tag(TAG).d("onLoadFailed: Error loading image");
                        pImageView.setImageResource(android.R.drawable.btn_dialog);
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
        return getDashBoardItems(context,fileName,false);
    }
    public ArrayList<DashBoardItem> getDashBoardItems(Context context, String fileName, boolean isSorting) {
        ArrayList<DashBoardItem> dashBoardItems = new ArrayList<>();
        try {
            Gson gson = new GsonBuilder()
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
            DashBoardGroup dashboardGroup = gson.fromJson(loadJSONFromAsset(context, fileName), DashBoardGroup.class);
            final List<Group> groups = dashboardGroup.getGroups();
            boolean isCountDisp = dashboardGroup.isCountDisplay();
            groups.forEach((group) -> {
                List<Child>  childes =  group.getChilds();
                if(null == childes){
                    childes = new ArrayList<>();
                }
                if(isSorting){
                    Collections.sort(childes, Comparator.comparing(o -> o.getChildName().toLowerCase()));
                }
                String count="";
                if(isCountDisp){
                    count  =childes.size()+"";
                }

                if(group.isVisible()){
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
            if(isSorting){
                Collections.sort(dashBoardItems, Comparator.comparing(o -> o.getName().toLowerCase()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            //DO Nothing
        }
        return dashBoardItems;

    }


    private String loadJSONFromAsset(Context context, String pFileName) {
        String json = null;
        try {

            InputStream is = context.getAssets().open(pFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public GridLayout getGridLayout(View includedLayout) {
        return (GridLayout) includedLayout.findViewById(com.verma.android.dashboard.R.id.child_board_grid);
    }

    public void setCountVisiable(boolean isVisable) {
    }
}
