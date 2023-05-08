package com.example.learnxandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class ViewCohort extends AppCompatActivity {
    TextView cohort2;
    JSONObject hello;
    MainActivity2 obj = new MainActivity2();
    JSONArray temp;
    String personEmail;
    JSONObject root;
    LinearLayout relativeLayout;
    Button button;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_cohort);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        relativeLayout = findViewById(R.id.relative);

        String URL = "http://10.0.2.2:8000/student/?format=json";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        ArrayList<String> list = new ArrayList<String>();
        JsonArrayRequest objectRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("Rest Response", response.toString());

                        //Getting cohort name to display
                        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(ViewCohort.this);
                        if (acct != null) {
                            personEmail = acct.getEmail();
                        }
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                button = new Button(ViewCohort.this);
                                button.setLayoutParams(new LinearLayout.LayoutParams(600, 150));
                                button.setId(i);
                                System.out.println("personEmail " + personEmail);
                                root = response.optJSONObject(i);
                                System.out.println("2nd " + root.getString("email"));
                                if (personEmail.equals(root.getString("email"))) {
                                    button.setText(root.getString("cohortName"));
                                    relativeLayout.addView(button);

                                }
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        openViewAssessment();
                                    }
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("Exception");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Response", error.toString());
                    }
                }
        );
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                //openViewAssessment();
//            }
//        });
        requestQueue.add(objectRequest);
        System.out.println(objectRequest);

    }
    public void openViewAssessment()
    {
        Intent intent = new Intent(this, ViewAssessment.class);
        startActivity(intent);
    }
}
