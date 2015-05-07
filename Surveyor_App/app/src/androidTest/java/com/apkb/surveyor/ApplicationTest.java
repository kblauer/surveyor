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
    public void setUp() {
        testArrayList = new ArrayList<>();
        mainActivity = new MainActivity();
    }

    @Test
    public void testNoRadioSubmitPressed() {
        //tests if no radio button was selected and submit was pressed
        ArrayList<Integer> testArrayList = new ArrayList<>();
        MainActivity mainActivity = new MainActivity();
        mainActivity.setSelected(testArrayList);
        mainActivity.getSelectedRadioButton();
        assertTrue(mainActivity.getToastText() == null);
    }

    @Test
    public void testInvalidDataSubmitPressed() {
        //tests if an invalid radio button value is present when submit is pressed
        testArrayList.add(0, -1);
        mainActivity.setSelected(testArrayList);
        mainActivity.getSelectedRadioButton();
        assertTrue(mainActivity.getToastText().equals("Invalid Selection"));
    }

    @Test
    public void testInRangeDataSubmitPressed() {
        //tests if in range data is present when submit is pressed
        String testData[] = new String[4];
        testData[3]="Green";
        mainActivity.setSurveyChoices(testData);
        testArrayList.add(0, 2);
        mainActivity.setSelected(testArrayList);
        mainActivity.getSelectedRadioButton();
        assertTrue(mainActivity.getToastText().equals("Green Submitted"));
    }

    @Test
    public void testAboveRangeDataSubmitPressed() {
        //tests if above the range data is present when submit is pressed

        testArrayList.add(0, 4);
        mainActivity.setSelected(testArrayList);
        mainActivity.getSelectedRadioButton();
        assertTrue(mainActivity.getToastText().equals("Invalid Selection"));
    }

    @Test
    public void testMultipleValuesSubmitPressed() {
        //tests if multiple values are present if the correct value is used when submit is pressed
        String testData[] = new String[4];
        testData[2]="Red";
        mainActivity.setSurveyChoices(testData);
        testArrayList.add(0, 1);
        testArrayList.add(1, 2);
        mainActivity.setSelected(testArrayList);
        mainActivity.getSelectedRadioButton();
        assertTrue(mainActivity.getToastText().equals("Red Submitted"));
    }

    @Test
    public void testLargeValueSubmitPressed() {
        //tests if an extremely large value is present when submit is pressed
        testArrayList.add(0, 32800);
        mainActivity.setSelected(testArrayList);
        mainActivity.getSelectedRadioButton();
        assertTrue(mainActivity.getToastText().equals("Invalid Selection"));
    }

    @Test
    public void testSmallValueSubmitPressed() {
        //tests if an extremely large value is present when submit is pressed
        testArrayList.add(0, -32800);
        mainActivity.setSelected(testArrayList);
        mainActivity.getSelectedRadioButton();
        assertTrue(mainActivity.getToastText().equals("Invalid Selection"));
    }

    @Test
    public void testBelowRangePosition() {
        //tests when a position is below the boundary
        position = -1;
        mainActivity.getNavigationItemSelected(position);
        assertTrue(mainActivity.getmTitle().equals("View Survey"));
    }

    @Test
    public void testInRangePosition() {
        //tests when a position is in the range
        position = 1;
        mainActivity.getNavigationItemSelected(position);
        assertTrue(mainActivity.getmTitle().equals("Section 2"));
    }

    @Test
    public void testAboveRangePosition() {
        //tests when a position is above the range
        position = 3;
        mainActivity.getNavigationItemSelected(position);
        assertTrue(mainActivity.getmTitle().equals("View Survey"));
    }

    @Test
    public void testLargePosition() {
        //tests a very large position value
        position = 32800;
        mainActivity.getNavigationItemSelected(position);
        assertTrue(mainActivity.getmTitle().equals("View Survey"));
    }

    @Test
    public void testSmallPosition() {
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

        assertEquals("Title does not match", correctResult[0], result[0]);
        assertEquals("First option does not match", correctResult[1], result[1]);
        assertEquals("Second option does not match", correctResult[2], result[2]);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Test
    public void testReadStream() {
        //tests valid string
        String testString = "Test String";
        InputStream stream = new ByteArrayInputStream((testString.getBytes(StandardCharsets.UTF_8)));
        ViewSurveyFragment.SurveyAsyncTask surveyAsyncTask = viewSurveyFragment.new SurveyAsyncTask();
        String result = surveyAsyncTask.readStream(stream);
        assertEquals(testString, result);

        //tests exception handling
        InputStream nullStream = null;
        result = surveyAsyncTask.readStream(nullStream);
        assertEquals(result, null);
    }

    @Test
    public void testFindSelectedRadioButton() {
        MainActivity mainActivity = new MainActivity();

        //test default
        mainActivity.findSelectedRadioButton(-1);
        assertTrue(-1 == mainActivity.getSelected().get(0));

        //test 0
        mainActivity.findSelectedRadioButton(0);
        assertTrue(0 == mainActivity.getSelected().get(0));

        //test 1
        mainActivity.findSelectedRadioButton(1);
        assertTrue(1 == mainActivity.getSelected().get(0));

        //test 2
        mainActivity.findSelectedRadioButton(2);
        assertTrue(2 == mainActivity.getSelected().get(0));

        //test 3
        mainActivity.findSelectedRadioButton(3);
        assertTrue(3 == mainActivity.getSelected().get(0));
    }

    @Test
    public void testSetTitle() {
        MainActivity mainActivity = new MainActivity();
        String title = "test Title";

        mainActivity.setmTitle(title);

        assertEquals(title, mainActivity.getmTitle());
    }

    @Test
    public void testGetSelectedRadioButton() {
        MainActivity mainActivity = new MainActivity();
        ArrayList<Integer> testSelected = new ArrayList<>(1);
        String surveyChoices[] = {"Title", "Blue", "Red", "Pink", "Black"};
        mainActivity.setSurveyChoices(surveyChoices);

        //test case 0
        testSelected.add(0, 0);
        mainActivity.setSelected(testSelected);
        mainActivity.getSelectedRadioButton();
        assertEquals(mainActivity.getToastText(), "Blue Submitted");

        //test case 3
        testSelected.add(0, 3);
        mainActivity.setSelected(testSelected);
        mainActivity.getSelectedRadioButton();
        assertEquals(mainActivity.getToastText(), "Black Submitted");
    }

    @Test
    public void testGetNavigationItemSelected() {
        MainActivity mainActivity = new MainActivity();

        //test position 0
        mainActivity.getNavigationItemSelected(0);
        assertEquals(mainActivity.getmTitle(), "View Survey");

        //test position 2
        mainActivity.getNavigationItemSelected(2);
        assertEquals(mainActivity.getmTitle(), "Section 3");
    }

    @Test
    public void testDoInBackground() {
        ViewSurveyFragment.SurveyAsyncTask surveyAsyncTask = viewSurveyFragment.new SurveyAsyncTask();

        //tests valid url
        surveyAsyncTask.setURLString("http://www.google.com");
        surveyAsyncTask.doInBackground();

        //tests invalid url
        surveyAsyncTask.setURLString("1");
        surveyAsyncTask.doInBackground();

    }

    @Test
    public void testParseResult() {
        ViewSurveyFragment.SurveyAsyncTask surveyAsyncTask = viewSurveyFragment.new SurveyAsyncTask();
        MainActivity mainActivity = new MainActivity();

        //tests null result
        surveyAsyncTask.result = null;
        surveyAsyncTask.parseResult();
        assertEquals(surveyAsyncTask.resultArray[0], "Select your favorite color");
        assertEquals(surveyAsyncTask.resultArray[1], "Blue");
        assertEquals(surveyAsyncTask.resultArray[2], "Red");
        assertEquals(surveyAsyncTask.resultArray[3], "Green");
        assertEquals(surveyAsyncTask.resultArray[4], "Pink");

        //tests valid result
        surveyAsyncTask.result = "{\"1\": \"red\", \"2\": \"blue\", \"title\": \"testSurvey\" }";
        surveyAsyncTask.parseResult();
        String correctResult[] = new String[5];
        correctResult[0] = "testSurvey";
        correctResult[1] = "red";
        correctResult[2] = "blue";

        assertEquals("Title does not match", correctResult[0], surveyAsyncTask.resultArray[0]);
        assertEquals("First option does not match", correctResult[1], surveyAsyncTask.resultArray[1]);
        assertEquals("Second option does not match", correctResult[2], surveyAsyncTask.resultArray[2]);
    }

}