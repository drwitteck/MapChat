package edu.temple.mapchat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PartnersFragment extends Fragment {
    private TextView results;
    private String data = "";
    private String userName;
    private Double latitude, longitude;
    private String stringLat, stringLong;
    private String getURL = "https://kamorris.com/lab/get_locations.php";
    private String postURL = "https://kamorris.com/lab/register_location.php";
    private RequestQueue requestQueue;
//    private PartnersInterface mPartnersInterface;

    public PartnersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_partners, container, false);

        FloatingActionButton addPartnerFab = v.findViewById(R.id.fab_add_partner);
        addPartnerFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });

//        Location location = mPartnersInterface.getUsersLocation();

        results = v.findViewById(R.id.json_data);

//        latitude = mPartnersInterface.getUsersLocation().getLatitude();
//        longitude = mPartnersInterface.getUsersLocation().getLongitude();
//        stringLat = Double.toString(latitude);
//        stringLong = Double.toString(longitude);

        // Empty the TextView
        results.setText("");

        getPartners();

        return v;
    }

    public void getPartners(){
        // Initialize a new RequestQueueSingleton instance
        requestQueue = RequestQueueSingleton.getInstance(getActivity())
                .getRequestQueue();

        // Initialize a new JsonObjectRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                getURL,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        // Process the JSON
                        try {
                            for (int i = 0; i < response.length(); i++){
                                // Get current json object
                                JSONObject jsonObject = (JSONObject) response.get(i);

                                // Get the current jsonObject (json object) data
                                String username = jsonObject.getString("username");
                                String latitude = jsonObject.getString("latitude");
                                String longitude = jsonObject.getString("longitude");

                                data += "Username: " + username + "\n\n";
                                data += "Latitude: " + latitude + "\n\n";
                                data += "Longitude: " + longitude + "\n\n";
                                data += "" + "\n\n";
                            }

                            results.setText(data);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.e("Volley", "Error");
                    }
                }
        );

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }

    public void addUser(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
        EditText partnerInput = new EditText(getContext());
        alertBuilder.setView(partnerInput);
        userName = partnerInput.getText().toString();

        alertBuilder.setTitle("Add New Partner")
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        postRequest();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    public void postRequest(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, postURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("POST Response", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", error.toString());
            }
        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("Username", userName);
                params.put("latitude", stringLat);
                params.put("longitude", stringLong);

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

//        if(activity instanceof PartnersInterface) {
//            mPartnersInterface = (PartnersInterface) activity;
//        } else {
//            throw new RuntimeException("Not Implemented");
//        }
    }

//    public interface PartnersInterface {
//        Location getUsersLocation();
//    }

    public void onClick(View view) {

    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Uri uri);
    }
}


//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//
//
//    }
//
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
////        if (context instanceof OnListFragmentInteractionListener) {
////            mListener = (OnListFragmentInteractionListener) context;
////        } else {
////            throw new RuntimeException(context.toString()
////                    + " must implement OnListFragmentInteractionListener");
////        }
//    }
//

//}
