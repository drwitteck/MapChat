package edu.temple.mapchat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartnersActivity extends AppCompatActivity {
    private TextView results;
    private String data = "";
    private String userName;
    private Double latitude, longitude;
    private String stringLat, stringLong;
    private String getURL = "https://kamorris.com/lab/get_locations.php";
    private String postURL = "https://kamorris.com/lab/register_location.php";
    private RequestQueue requestQueue;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    List<Partners> dataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partners);

        results = findViewById(R.id.json_data);

        FloatingActionButton addPartnerFab = findViewById(R.id.fab_add_partner);
        addPartnerFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });

        requestQueue = RequestQueueSingleton.getInstance(this)
                .getRequestQueue();

        // Initialize a new JsonObjectRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                getURL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Process the JSON
                        try {
                            for (int i = 0; i < response.length(); i++) {
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

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }

    public void addUser(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        EditText partnerInput = new EditText(this);
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

                params.put("User", userName);
                params.put("latitude", stringLat);
                params.put("longitude", stringLong);

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    public void onClick(View view) {
        String name = "Phil";
        Toast.makeText(this, "Name" + name, Toast.LENGTH_SHORT).show();
        Intent chatIntent = new Intent(this, ChatActivity.class);
        chatIntent.putExtra("username", name);
        startActivity(chatIntent);
    }
}
