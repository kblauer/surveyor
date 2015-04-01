package com.apkb.surveyor;

import android.app.Application;
import android.test.ApplicationTestCase;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ApplicationTest extends ApplicationTestCase<Application> {
    ArrayList<Integer> testArrayList;
    MainActivity mainActivity;
    int position;

    public ApplicationTest() {
        super(Application.class);
    }

    @Before
    public void setUp(){
        testArrayList = new ArrayList<>();
        mainActivity = new MainActivity();
    }

    @Test
    public void testNoRadioSubmitPressed(){
        //tests if no radio button was selected and submit was pressed
        ArrayList<Integer> testArrayList = new ArrayList<>();
        MainActivity mainActivity = new MainActivity();
        mainActivity.setSelected(testArrayList);
        mainActivity.getSelectedRadioButton();
        assertTrue(mainActivity.getToastText() == null);
    }

    @Test
    public void testInvalidDataSubmitPressed(){
        //tests if an invalid radio button value is present when submit is pressed
        testArrayList.add(0, -1);
        mainActivity.setSelected(testArrayList);
        mainActivity.getSelectedRadioButton();
        assertTrue(mainActivity.getToastText().equals("Invalid Selection"));
    }

    @Test
    public void testInRangeDataSubmitPressed(){
        //tests if in range data is present when submit is pressed
        testArrayList.add(0, 2);
        mainActivity.setSelected(testArrayList);
        mainActivity.getSelectedRadioButton();
        assertTrue(mainActivity.getToastText().equals("Green Submitted"));
    }

    @Test
    public void testAboveRangeDataSubmitPressed(){
        //tests if above the range data is present when submit is pressed
        testArrayList.add(0, 4);
        mainActivity.setSelected(testArrayList);
        mainActivity.getSelectedRadioButton();
        assertTrue(mainActivity.getToastText().equals("Invalid Selection"));
    }

    @Test
    public void testMultipleValuesSubmitPressed(){
        //tests if multiple values are present if the correct value is used when submit is pressed
        testArrayList.add(0,1);
        testArrayList.add(1,2);
        mainActivity.setSelected(testArrayList);
        mainActivity.getSelectedRadioButton();
        assertTrue(mainActivity.getToastText().equals("Red Submitted"));
    }

    @Test
    public void testLargeValueSubmitPressed(){
        //tests if an extremely large value is present when submit is pressed
        testArrayList.add(0,32800);
        mainActivity.setSelected(testArrayList);
        mainActivity.getSelectedRadioButton();
        assertTrue(mainActivity.getToastText().equals("Invalid Selection"));
    }

    @Test
    public void testSmallValueSubmitPressed(){
        //tests if an extremely large value is present when submit is pressed
        testArrayList.add(0,-32800);
        mainActivity.setSelected(testArrayList);
        mainActivity.getSelectedRadioButton();
        assertTrue(mainActivity.getToastText().equals("Invalid Selection"));
    }

    @Test
    public void testBelowRangePosition(){
        //tests when a position is below the boundary
        position = -1;
        mainActivity.getNavigationItemSelected(position);
        assertTrue(mainActivity.getmTitle().equals("View Survey"));
    }

    @Test
    public void testInRangePosition(){
        //tests when a position is in the range
        position = 1;
        mainActivity.getNavigationItemSelected(position);
        assertTrue(mainActivity.getmTitle().equals("Section 2"));
    }

    @Test
    public void testAboveRangePosition(){
        //tests when a position is above the range
        position = 3;
        mainActivity.getNavigationItemSelected(position);
        assertTrue(mainActivity.getmTitle().equals("View Survey"));
    }

    @Test
    public void testLargePosition(){
        //tests a very large position value
        position = 32800;
        mainActivity.getNavigationItemSelected(position);
        assertTrue(mainActivity.getmTitle().equals("View Survey"));
    }

    @Test
    public void testSmallPosition(){
        //tests a very small position value
        position = -32800;
        mainActivity.getNavigationItemSelected(position);
        assertTrue(mainActivity.getmTitle().equals("View Survey"));
    }
}