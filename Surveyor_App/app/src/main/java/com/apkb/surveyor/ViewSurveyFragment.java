package com.apkb.surveyor;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewSurveyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewSurveyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewSurveyFragment extends Fragment {
    private final static String TAG = "ViewSurveyFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewSurveyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewSurveyFragment newInstance(String param1, String param2) {
        ViewSurveyFragment fragment = new ViewSurveyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ViewSurveyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            new SurveyAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            new SurveyAsyncTask().execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_survey, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        getActivity().setTitle(getString(R.string.view_survey_title));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public class SurveyAsyncTask extends AsyncTask<String, String, Void> {
        InputStream inputStream = null;
        String result = null;
        String resultArray[] = null;

        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(String... params){
            URL url;
            try{
                url = new URL("http://ec2-52-11-126-61.us-west-2.compute.amazonaws.com/survey/json/1");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try{
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    result = readStream(inputStream);

                } finally {
                    urlConnection.disconnect();
                }
            } catch(Exception e) {
                Log.e(TAG, "Error in doInBackground", e);

            }

            return null;
        }



        protected void onPostExecute(Void v){
            if(result != null){
                resultArray = parseJSON(result);
            } else {
                resultArray = new String[5];
                resultArray[0] = "Select your favorite color";
                resultArray[1] = "Blue";
                resultArray[2] = "Red";
                resultArray[3] = "Green";
                resultArray[4] = "Pink";
            }

            buildSurveyGUI(resultArray);

        }

        private String readStream(InputStream inputStream) {
            StringBuilder stringBuilder = null;
            String tempString;
            try {
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                stringBuilder = new StringBuilder();

                while ((tempString = streamReader.readLine()) != null)
                    stringBuilder.append(tempString);
            } catch (Exception e) {
                Log.e(TAG, "Error in readStream", e);
            }

            return stringBuilder.toString();

        }

        public String[] parseJSON(String result) {
            String surveyList[] = new String[11];
            try{
                JSONObject jsonObject = new JSONObject(result);
                for (int i = 0; i < 11; i++) {
                    if(i==0){
                        surveyList[i] = jsonObject.getString("title");

                    }else {
                        surveyList[i] = jsonObject.getString((new Integer(i)).toString());
                    }
                }


            } catch (Exception e) {
                Log.e(TAG, "Error in parseJSON", e);
            }

            return surveyList;
        }

        private void buildSurveyGUI(String [] resultArray){
            FrameLayout surveyLayout = (FrameLayout) getView().findViewById(R.id.view_survey);
            TextView title = (TextView)getView().findViewById(R.id.survey_title);
            final RadioGroup rg = (RadioGroup)getView().findViewById(R.id.survey_rg);
            View.OnClickListener radioListener = new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    ((MainActivity)getActivity()).onRadioButtonClicked(rg);
                }
            };

            //grabs title from array and changes the text in the view
            title.setText(resultArray[0]);


            for (int i = 1; i <resultArray.length ; i++) {
                RadioButton rb = new RadioButton(getActivity());
                rb.setId(View.generateViewId());
                rb.setText(resultArray[i]);
                rb.setOnClickListener(radioListener);
                rg.addView(rb);

            }
        }
    }

}
