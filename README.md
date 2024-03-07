# DashBoard
---
DashBoard
---

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.vermasourav:DashBoard:1.0.9'
	}


---
  implementation 'com.github.vermasourav:DashBoard:1.0.9'
---

<h2>XML Code</h2>
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
<h2>Android Code</h2>
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
```
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
      "childs": []
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
```
    <com.verma.android.dashboard.expendview.CustomExpandableListView
    android:id="@+id/expandable_listview"
    android:layout_width="wrap_content"
    android:layout_height="300dp"
    android:childDivider="@android:color/holo_red_dark"
    android:divider="#000000"
    android:dividerHeight="0dp"
    app:withChildArrow="true"
    app:withImage= "true"
    app:withSorting="false"
    app:childMode="listMode"
    tools:listitem="@layout/expended_view_childs">
    </com.verma.android.dashboard.expendview.CustomExpandableListView>


        private void intExpendedList() {
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
---
