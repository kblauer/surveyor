package com.apkb.surveyor;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, ViewSurveyFragment.OnFragmentInteractionListener {

    private final static String TAG = "MainActivity";

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private String toastText;
    private ArrayList<Integer> selected= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragment = getNavigationItemSelected(position);

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

    }

    public Fragment getNavigationItemSelected(int position){
        Fragment fragment;
        switch(position) {
            default:
                fragment = new ViewSurveyFragment();
                mTitle = "View Survey";
                break;
            case 0:
                fragment = new ViewSurveyFragment();
                mTitle = "View Survey";
                break;
            case 1:
                fragment = PlaceholderFragment.newInstance(position +1);
                mTitle = "Section 2";
                break;
            case 2:
                fragment = PlaceholderFragment.newInstance(position +1);
                mTitle = "Section 3";
                break;
        }
        return fragment;
    }


    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void onRadioButtonClicked(View view){
        int buttonID = ((RadioGroup) view).getCheckedRadioButtonId();
        View radioButton = ((RadioGroup) view).findViewById(buttonID);
        int index = ((RadioGroup) view).indexOfChild(radioButton);
        findSelectedRadioButton(index);


    }
    public void findSelectedRadioButton(int index) {
        //Check which button was clicked
        switch(index) {
            default:
                selected.add(-1);
                break;
            case 0:
                selected.add(0, 0);
                break;
            case 1:
                selected.add(0, 1);
                break;
            case 2:
                selected.add(0, 2);
                break;
            case 3:
                selected.add(0, 3);
                break;
        }
    }

    public void submitButtonClicked(View view){
        //TODO: Submit the data to the server

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast;

        getSelectedRadioButton();
        toast = Toast.makeText(context, toastText, duration);
        toast.show();

        }

        public void getSelectedRadioButton(){
            if(!selected.isEmpty()){
                switch(selected.get(0)){
                    default:
                        toastText = "Invalid Selection";
                        break;
                    case 0:
                        toastText = "Blue Submitted";
                        break;
                    case 1:
                        toastText = "Red Submitted";
                        break;
                    case 2:
                        toastText = "Green Submitted";
                        break;
                    case 3:
                        toastText = "Pink Submitted";
                        break;

                }
    }

    }

    public CharSequence getmTitle() {
        return mTitle;
    }

    public void setmTitle(CharSequence mTitle) {
        this.mTitle = mTitle;
    }

    public ArrayList<Integer> getSelected() {
        return selected;
    }

    public void setSelected(ArrayList<Integer> selected) {
        this.selected = selected;
    }

    public String getToastText() {
        return toastText;
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

    }

}


