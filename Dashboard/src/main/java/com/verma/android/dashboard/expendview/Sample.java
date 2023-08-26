package com.verma.android.dashboard.expendview;

import com.verma.android.dashboard.DashBoardItem;
import com.verma.android.dashboard.pojo.Child;
import com.verma.android.dashboard.pojo.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Sample {

    private static final String TAG = "Sample";

    public List<DashBoardItem> getGroupList(int headerCount, Boolean asListView) {
        List<DashBoardItem> headerList = new ArrayList<>();
        for (int i = 0; i < headerCount; i++) {
            if (null == asListView) {
                headerList.add(getHeader(generateRandomId(11, 20)));
            } else {
                headerList.add(getHeader(generateRandomId(1, 10)));
            }
        }
        return headerList;

    }

    public DashBoardItem getHeader(int childCount) {
        List<Child> childList = new ArrayList<>();
        for (int i = 0; i < childCount; i++) {
            childList.add(getChild());
        }
        DashBoardItem item =
                new DashBoardItem.DashBoardItemBuilder()
                        .setURL("https://picsum.photos/200/300?random="+generateRandomId(1,50))
                        .setName(generateRandomName())
                        .setVisible(true)
                        .setChilds(childList)
                        .build();
        return item;

    }

    public Child getChild() {
        return new Child()
                .withId(generateRandomId())
                .withName(generateRandomName())
                //.withThumbnail("https://cdn-icons-png.flaticon.com/512/566/566245.png")
                .withThumbnail("https://picsum.photos/200/300?random="+generateRandomId(1,50))
                .withDescription(generateRandomName(generateRandomId(10,100)));
    }

    private String generateRandomName() {
        return generateRandomName(15);
    }

    public boolean generateRandomBoolean() {
        return new Random().nextBoolean();
    }

    private String generateRandomName(int pLength) {
        String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String space = " ";
        String alphaNumeric = space + upperAlphabet + lowerAlphabet + numbers;
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < pLength; i++) {
            int index = random.nextInt(alphaNumeric.length());
            char randomChar = alphaNumeric.charAt(index);
            sb.append(randomChar);
            if(i % 20 == 1){
                sb.append(space);
            }
        }
        return sb.toString();
    }

    private int generateRandomId() {
        return generateRandomId(50, Integer.MAX_VALUE);
    }

    private int generateRandomId(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }
}
