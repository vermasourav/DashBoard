package com.verma.android.dashboard.window;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.verma.android.dashboard.DashBoardItem;
import com.verma.android.dashboard.DashBoardWindowItem;
import com.verma.android.dashboard.DashboardClickListener;
import com.verma.android.dashboard.R;
import com.verma.android.dashboard.Setup;
import com.verma.android.dashboard.databinding.WindowDashboardItemOneBinding;
import com.verma.android.dashboard.databinding.WindowDashboardItemThreeBinding;
import com.verma.android.dashboard.databinding.WindowDashboardItemTwoBinding;

import java.util.List;

public class WindowAdapter extends RecyclerView.Adapter {

    Setup setup = new Setup();
    private static final int VIEW_TYPE_ZERO_COLUMN = 0;
    private static final int VIEW_TYPE_ONE_COLUMN = 1;
    private static final int VIEW_TYPE_TWO_COLUMN_A = 2;
    private static final int VIEW_TYPE_TWO_COLUMN_B = 3;
    private static final int VIEW_TYPE_THREE_COLUMN_A = 4;
    private static final int VIEW_TYPE_THREE_COLUMN_B = 5;

    private final Context mContext;
    private List<DashBoardWindowItem> dashBoardWindowItems;

    private DashboardClickListener listener;

    private final int[] tileColors = {
            Color.parseColor("#0078D7"), // Windows blue
            Color.parseColor("#E81123"), // Windows red
            Color.parseColor("#107C10"), // Windows green
            Color.parseColor("#FFB900"), // Windows yellow
            Color.parseColor("#744DA9"), // Windows purple
            Color.parseColor("#038387"), // Windows teal
            Color.parseColor("#CA5010"), // Windows orange
            Color.parseColor("#8764B8")  // Windows light purple
    };

    public void setSetup(Setup setup) {
        this.setup = setup;
    }

    public WindowAdapter(Context context, List<DashBoardWindowItem> messageList, DashboardClickListener dashboardClickListener) {
        mContext = context;
        dashBoardWindowItems = messageList;
        setDashboardClickListener(dashboardClickListener);
    }

    public void setDashboardClickListener(DashboardClickListener dashboardClickListener) {
        this.listener = dashboardClickListener;
    }

    @Override
    public int getItemCount() {
        return dashBoardWindowItems.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        int count = dashBoardWindowItems.get(position).dashBoardItems.size();
        boolean isEvenPosition = (position % 2) == 0;
        if (1 == count) {
            return VIEW_TYPE_ONE_COLUMN;
        } else if (2 == count) {
            return isEvenPosition ? VIEW_TYPE_TWO_COLUMN_A : VIEW_TYPE_TWO_COLUMN_B;
        } else if (3 == count) {
            return isEvenPosition ? VIEW_TYPE_THREE_COLUMN_A : VIEW_TYPE_THREE_COLUMN_B;
        }
        return VIEW_TYPE_ZERO_COLUMN;
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        int[] currentTileColors = {tileColors[0], tileColors[1], tileColors[2]};

        switch (viewType) {
            case VIEW_TYPE_ONE_COLUMN:
                currentTileColors[0] = tileColors[1];
                view = inflater.inflate(R.layout.window_dashboard_item_one, parent, false);
                return new OneColumnHolder(view, currentTileColors, setup, listener);
            case VIEW_TYPE_TWO_COLUMN_A:
                currentTileColors[0] = tileColors[1];
                currentTileColors[1] = tileColors[2];
                view = inflater.inflate(R.layout.window_dashboard_item_two, parent, false);
                return new TwoColumnHolder(view, currentTileColors, setup, listener);
            case VIEW_TYPE_TWO_COLUMN_B:
                currentTileColors[0] = tileColors[3];
                currentTileColors[1] = tileColors[4];
                view = inflater.inflate(R.layout.window_dashboard_item_two, parent, false);
                return new TwoColumnHolder(view, currentTileColors, setup, listener);
            case VIEW_TYPE_THREE_COLUMN_A:
                currentTileColors[0] = tileColors[0];
                currentTileColors[1] = tileColors[1];
                currentTileColors[2] = tileColors[2];
                view = inflater.inflate(R.layout.window_dashboard_item_three, parent, false);
                return new ThreeColumnHolder(view, currentTileColors, setup, listener);
            case VIEW_TYPE_THREE_COLUMN_B:
                currentTileColors[0] = tileColors[3];
                currentTileColors[1] = tileColors[4];
                currentTileColors[2] = tileColors[5];
                view = inflater.inflate(R.layout.window_dashboard_item_three, parent, false);
                return new ThreeColumnHolder(view, currentTileColors, setup, listener);
            default:
                // Handle default case or throw an exception for unknown viewType
                View defaultView = inflater.inflate(R.layout.window_dashboard_item_one, parent, false); // Example default
                return new OneColumnHolder(defaultView, new int[]{tileColors[0]}, setup, listener); // Example default
        }
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        List<DashBoardItem> dashBoardItems = dashBoardWindowItems.get(position).dashBoardItems;
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_ONE_COLUMN:
                ((OneColumnHolder) holder).bind(dashBoardItems);
                break;
            case VIEW_TYPE_TWO_COLUMN_A:
            case VIEW_TYPE_TWO_COLUMN_B:
                ((TwoColumnHolder) holder).bind(dashBoardItems);
                break;
            case VIEW_TYPE_THREE_COLUMN_A:
            case VIEW_TYPE_THREE_COLUMN_B:
                ((ThreeColumnHolder) holder).bind(dashBoardItems);
                break;
        }
    }


    private static class OneColumnHolder extends RecyclerView.ViewHolder {
        private final WindowDashboardItemOneBinding binding;
        private final int[] colors;
        DashboardClickListener listener;

        OneColumnHolder(View itemView, int[] tileColor, Setup setup, DashboardClickListener listener) {
            super(itemView);
            binding = WindowDashboardItemOneBinding.bind(itemView);
            binding.setTileColors(tileColor[0]);
            binding.setSetup(setup);
            colors = tileColor;
            this.listener = listener;
        }

        void bind(List<DashBoardItem> dashBoardItems) {
            if (dashBoardItems != null && !dashBoardItems.isEmpty()) {
                final DashBoardItem item = dashBoardItems.get(0);
                binding.setItem(item);
                binding.setTileColors(colors[0]);
                binding.getRoot().setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onClick(v, item);
                    }
                });
                setChildImage(binding.thumbnail,binding.getSetup().isImageDisplay(), binding.getRoot().getContext(), item.getUrl());
            }
        }
    }

     public static void setChildImage(ImageView thumbnail,boolean isImageDisplay, Context context, String pUrl) {
        if (isImageDisplay) {
                thumbnail.setVisibility(View.VISIBLE);
                thumbnail.setImageResource(R.drawable.no_image);
                if (!TextUtils.isEmpty(pUrl)) {
                    Glide.with(context)
                            .load(pUrl)
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

    private static class TwoColumnHolder extends RecyclerView.ViewHolder {
        WindowDashboardItemTwoBinding binding;
        int[] colors;
        DashboardClickListener listener;

        TwoColumnHolder(View itemView, int[] tileColor, Setup setup, DashboardClickListener listener) {
            super(itemView);
            binding = WindowDashboardItemTwoBinding.bind(itemView);
            binding.setSetup(setup);
            colors = tileColor;
            this.listener = listener;
        }

        void bind(List<DashBoardItem> dashBoardItems) {
            if (dashBoardItems != null && !dashBoardItems.isEmpty()) {
                binding.setTileColors(colors);
                final DashBoardItem itemA = dashBoardItems.get(0);
                final DashBoardItem itemB = dashBoardItems.get(1);
                binding.setItemA(itemA);
                binding.setItemB(itemB);
                setChildImage(binding.layoutItemA.thumbnail,binding.getSetup().isImageDisplay(), binding.getRoot().getContext(), itemA.getUrl());
                setChildImage(binding.layoutItemB.thumbnail, binding.getSetup().isImageDisplay(), binding.getRoot().getContext(), itemB.getUrl());

                binding.layoutItemA.getRoot().setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onClick(v, itemA);
                    }
                });
                binding.layoutItemB.getRoot().setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onClick(v, itemB);

                    }
                });
            }
        }
    }

    private static class ThreeColumnHolder extends RecyclerView.ViewHolder {
        private final WindowDashboardItemThreeBinding binding;
        private final int[] colors;
        DashboardClickListener listener;

        ThreeColumnHolder(View itemView, int[] tileColor, Setup setup, DashboardClickListener listener) {
            super(itemView);
            binding = WindowDashboardItemThreeBinding.bind(itemView);
            binding.setSetup(setup);
            binding.setTileColors(tileColor);
            colors = tileColor;
            this.listener = listener;
        }

        void bind(List<DashBoardItem> dashBoardItems) {
            if (dashBoardItems != null && !dashBoardItems.isEmpty()) {
                binding.setTileColors(colors);
                final DashBoardItem itemA = dashBoardItems.get(0);
                final DashBoardItem itemB = dashBoardItems.get(1);
                final DashBoardItem itemC = dashBoardItems.get(2);
                binding.setItemA(itemA);
                binding.setItemB(itemB);
                binding.setItemC(itemC);
                setChildImage(binding.layoutItemA.thumbnail,binding.getSetup().isImageDisplay(), binding.getRoot().getContext(), itemA.getUrl());
                setChildImage(binding.layoutItemB.thumbnail, binding.getSetup().isImageDisplay(), binding.getRoot().getContext(), itemB.getUrl());
                setChildImage(binding.layoutItemC.thumbnail,binding.getSetup().isImageDisplay(), binding.getRoot().getContext(), itemC.getUrl());

                binding.layoutItemA.getRoot().setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onClick(v, itemA);
                    }
                });
                binding.layoutItemB.getRoot().setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onClick(v, itemB);
                    }
                });
                binding.layoutItemC.getRoot().setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onClick(v, itemC);
                    }
                });
            }
        }
    }
}