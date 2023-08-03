# DashBoard
DashBoard

XML Code
---
 <ScrollView
                android:id="@+id/scrollable"
                android:layout_width="fill_parent"
                android:layout_height="match_parent">

                <GridLayout
                    android:id="@+id/dash_board_grid"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:alignmentMode="alignMargins"
                    android:columnCount="2"
                    android:horizontalSpacing="8dp"
                    android:footerDividersEnabled="false"
                    android:columnOrderPreserved="false"
                    android:padding="8dp" />
            </ScrollView>
---
Android Code

---
  


     public void setupDashboard() {
        setupGrid();
        DashBoardManager dashBoardManager = new DashBoardManager();
        ArrayList<DashBoardItem> dashBoardItems = dashBoardManager.getDashBoardItems(getContext(),"content_dashboard.json");
        Collections.sort(dashBoardItems, Comparator.comparing(o -> o.getName().toLowerCase()));
        dashBoardManager.setupDashboard(getContext(),binding.dashBoardGrid,3,dashBoardItems,dashboardClickListener);

    }
    DashboardClickListener dashboardClickListener = (v, dashBoardItem) -> {
        if(dashBoardItem.getChilds() != null){
            Timber.tag(TAG).d("onClick: %s", dashBoardItem.getChilds().toString());
            Menu00FragmentDirections.ActionNavHomeToNavOne action = Menu00FragmentDirections.actionNavHomeToNavOne();
                action.setHEADER(dashBoardItem.getName());
                action.setCHILDS(new Gson().toJson(dashBoardItem.getChilds()));


            Navigation.findNavController(v).navigate((NavDirections) action);


        }
    };

    private void setupGrid() {
        int orientation = getResources().getConfiguration().orientation;
        int spanCount;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            spanCount = 3;
        } else {
            spanCount = 4;
        }
        binding.dashBoardGrid.setColumnCount(spanCount);
    }
---

Json File at asserts 

---
content_dashboard.json
{
  "groups": [
    {
      "from": "l",
      "visible": true,  "name": "Date and Time",
      "image_url": "https://toppng.com/uploads/preview/time-and-date-icon-11549792838b97dvthvmd.png",
      "childs": [{
      "visible": true, "name": "Internet",
      "image_url": "https://toppng.com/uploads/preview/internet-png-transparent-internet-internet-clipart-11563103281ozh1yuvgcl.png",
      "childs": [
        {"child_visible": true, "child_id": 12001, "child_name": "IP Subnet Calculator"},
        {"child_visible": true, "child_id": 12002, "child_name": "Password Generator"},
        {"child_visible": true, "child_id": 12002, "child_name": "Bandwidth Calculator"}
      ]
    },
    {
      "visible": true, "name": "Engineering Unit",
      "image_url": "",
      "childs": [
        {"child_visible": true, "child_id": 13001, "child_name": "Temperature"},
        {"child_visible": true, "child_id": 13002, "child_name": "Angle"},
        {"child_visible": true, "child_id": 13002, "child_name": "Moment Of Force / Torque"}
      ]
    }
  ]
}

---
