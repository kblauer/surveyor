package com.apkb.surveyor;

import android.annotation.TargetApi;
import android.app.Application;
import android.os.Build;
import android.test.ApplicationTestCase;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ApplicationTest extends ApplicationTestCase<Application> {
    ArrayList<Integer> testArrayList;
    MainActivity mainActivity;
    ViewSurveyFragment viewSurveyFragment = new ViewSurveyFragment();
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

    @Test
    public void testParseJSON() {
        String testString = "{\"1\": \"red\", \"2\": \"blue\", \"title\": \"testSurvey\" }";
        String result[];

        String correctResult[] = new String[5];
        correctResult[0] = "testSurvey";
        correctResult[1] = "red";
        correctResult[2] = "blue";


        ViewSurveyFragment.SurveyAsyncTask surveyAsyncTask = viewSurveyFragment.new SurveyAsyncTask();
        result = surveyAsyncTask.parseJSON(testString);

        assertEquals("Title does not match",correctResult[0], result[0]);
        assertEquals("First option does not match", correctResult[1], result[1]);
        assertEquals("Second option does not match", correctResult[2], result[2]);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Test
    public void testReadStream(){
        String testString = "Test String";
        InputStream stream = new ByteArrayInputStream((testString.getBytes(StandardCharsets.UTF_8)));
        ViewSurveyFragment.SurveyAsyncTask surveyAsyncTask = viewSurveyFragment.new SurveyAsyncTask();
        String result = surveyAsyncTask.readStream(stream);
        assertEquals(testString, result);
    }

//    @Test
//    public void testBuildSurveyGUI(){
//        String strings[] = new String[10];
//        String testTitle = "Test Title";
//        strings[0] = testTitle;
//        strings[1] = "test 1";
//        strings[2] = "test 2";
//        ViewSurveyFragment.SurveyAsyncTask surveyAsyncTask = viewSurveyFragment.new SurveyAsyncTask();
//        RadioGroup rg = surveyAsyncTask.buildSurveyGUI(strings);
//
//        assertEquals("Title does not match", surveyAsyncTask.title, testTitle);
//        assertEquals("Incorrect number of radio buttons", rg.getChildCount(), 2);
//    }
}