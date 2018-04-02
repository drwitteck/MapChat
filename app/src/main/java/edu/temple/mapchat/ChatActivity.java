package edu.temple.mapchat;

import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private final String messagePostUrl = "https://kamorris.com/lab/send_message.php";
    RequestQueue requestQueue;
    String userName = "Derek";
    String partnerUsername;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        EditText input = findViewById(R.id.input);
        message = input.getText().toString();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            partnerUsername = extras.getString("username");
        }
        Log.e("USER", partnerUsername);

        requestQueue = RequestQueueSingleton.getInstance(this)
                .getRequestQueue();

        FloatingActionButton fab = findViewById(R.id.fab_send_message);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, messagePostUrl,
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
                        params.put("partneruser", partnerUsername);
                        params.put("message", message);

                        return params;
                    }
                };

                requestQueue.add(stringRequest);

            }
        });
    }
}
