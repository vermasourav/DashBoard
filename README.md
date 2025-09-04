
# DashBoard Library

DashBoard is an Android library designed to create customizable dashboards with expandable lists and grid layouts.

## Installation

Add the JitPack repository to your root `build.gradle`:

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency in your app's `build.gradle`:

```groovy
dependencies {
    implementation 'com.github.vermasourav:DashBoard:1.0.20'
}
```

## Usage

### XML Layout
Add a `GridLayout` in your XML file:

```xml
<GridLayout
    android:id="@+id/dash_board_grid"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:columnCount="2"
    android:padding="8dp"/>
```

### Java Code
Configure the dashboard in your activity or fragment:

```java
public void setupDashboard() {
    setupGrid();
    DashBoardManager dashBoardManager = new DashBoardManager();

    Setup setup    = new Setup();
    setup.setDebugLog(true);
    setup.setCountDisplay(true);
    setup.setImageDisplay(true);
    setup.setIsDiscriptionDisplay(true);
    dashBoardManager.setSetup(setup);
    
    ArrayList<DashBoardItem> dashBoardItems = dashBoardManager.getDashBoardItems(getContext(), "content_dashboard.json");
    Collections.sort(dashBoardItems, Comparator.comparing(o -> o.getName().toLowerCase()));
    dashBoardManager.setupDashboard(getContext(), binding.dashBoardGrid, 3, dashBoardItems, dashboardClickListener);
}

DashboardClickListener dashboardClickListener = (v, dashBoardItem) -> {
    if (dashBoardItem.getChilds() != null) {
         if(DashBoardManager.isDebugLogs()) {
             Log.d(TAG, ": onClick: "+ dashBoardItem.getChilds().toString());
         }
		Menu00FragmentDirections.ActionNavHomeToNavOne action = Menu00FragmentDirections.actionNavHomeToNavOne();
        action.setHEADER(dashBoardItem.getName());
        action.setCHILDS(new Gson().toJson(dashBoardItem.getChilds()));
        Navigation.findNavController(v).navigate(action);
    }
};

private void setupGrid() {
    int orientation = getResources().getConfiguration().orientation;
    int spanCount = (orientation == Configuration.ORIENTATION_PORTRAIT) ? 3 : 4;
    binding.dashBoardGrid.setColumnCount(spanCount);
}
```

### JSON Configuration
Define dashboard items in `content_dashboard.json` in the `assets` folder:

```json
{
    "display_count": true,
    "groups": [
        {
            "description": "Description of Date and Time",
            "visible": true,
            "name": "Date and Time",
            "image_url": "https://example.com/image.png",
            "childs": [
                {
                    "visible": true,
                    "name": "Internet",
                    "image_url": "",
                    "childs": []
                },
                {
                    "description": "Description of Engineering Unit",
                    "visible": true,
                    "name": "Engineering Unit",
                    "image_url": "",
                    "childs": [
                        {"visible": true, "id": 1300, "name": "A", "description": "Description of A", "thumbnail": ""},
                        {"visible": true, "id": 13001, "name": "Temperature"},
                        {"visible": true, "id": 13002, "name": "Angle"},
                        {"visible": true, "id": 13002, "name": "Torque"}
                    ]
                }
            ]
        }
    ]
}
```

# Expended List Library

### XML Configuration

```xml
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
```
 <attr name="childMode" format="integer">
            <enum name="listMode" value="0" />
            <enum name="window" value="1" />
            <enum name="dashboard" value="2" />
            <enum name="slide" value="3" />
        </attr>

```java
	private void intExpendedList() {
		binding.expandableListview.isWithImage(false);
		binding.expandableListview.isWithSorting(false);
		binding.expandableListview.isWithChildArrow(true);
		binding.expandableListview.withChildMode(1);
		
		binding.expandableListview.setGroupClickListener((group, groupPos) -> {
			binding.expandableListview.getGroups().get(groupPos);
            if(DashBoardManager.isDebugLogs()) {
                Log.d(TAG, ": You clicked :: "+ group.getName());
            }
		});
		
		binding.expandableListview.setChildClickListener((child, groupPos, childPos, header) -> {
            if(DashBoardManager.isDebugLogs()) {
                Log.d(TAG, ": You clicked :: "+ group.getChildName());
            }
		});
		
		DashBoardManager dashBoardManager = new DashBoardManager();
		ArrayList<DashBoardItem> dashBoardItems = dashBoardManager.getDashBoardItems(this,"content_dashboard.json");
		binding.expandableListview.doUpdate(dashBoardItems);
		
		// test();
	}
```


```java
    private void intWindowist() {

        DashBoardManager dashBoardManager = new DashBoardManager();
        Setup setup = new Setup();
        setup.setDebugLog(false);
        setup.setCountDisplay(true);
        setup.setImageDisplay(false);
        setup.setDescriptionDisplay(true);
        dashBoardManager.setSetup(setup);
        binding.expandableListview.withChildMode(2);

        binding.expandableListview.setGroupClickListener((group, groupPos) -> {
			binding.expandableListview.getGroups().get(groupPos);
            if(DashBoardManager.isDebugLogs()) {
                Log.d(TAG, ": You clicked :: "+ group.getName());
            }
		});
		
		binding.expandableListview.setChildClickListener((child, groupPos, childPos, header) -> {
            if(DashBoardManager.isDebugLogs()) {
                Log.d(TAG, ": You clicked :: "+ group.getChildName());
            }
		});
		
		DashBoardManager dashBoardManager = new DashBoardManager();
		ArrayList<DashBoardItem> dashBoardItems = dashBoardManager.getDashBoardItems(this,"content_dashboard.json");
		binding.expandableListview.doUpdate(dashBoardItems);
        
	}
```


```java
    public void setupDashboard() {
        DashBoardManager dashBoardManager = new DashBoardManager();
        Setup setup = new Setup();
        setup.setDebugLog(false);
        setup.setCountDisplay(true);
        setup.setImageDisplay(false);
        setup.setDescriptionDisplay(true);
        dashBoardManager.setSetup(setup);

        View includedLayout = findViewById(R.id.dashboard);
        ArrayList<DashBoardItem> dashBoardItems = dashBoardManager.getDashBoardItems(this, "content_dashboard.json");
        Collections.sort(dashBoardItems, Comparator.comparing(o -> o.getName().toLowerCase()));
        dashBoardManager.setupDashboard(this, dashBoardManager.getGridLayout(includedLayout), 2, dashBoardItems, dashboardClickListener);
    }
```



For more details, visit the [GitHub repository](https://github.com/vermasourav/DashBoard).
