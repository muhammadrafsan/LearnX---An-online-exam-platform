package com.example.learnxandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewAssessment extends AppCompatActivity {
    TextView cohort2;
    JSONObject hello;
    JSONArray temp;
    String personEmail;
    JSONObject root;
    LinearLayout relativeLayout;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_assessment);
        // Get the Intent that started this activity and extract the string

        Intent intent = getIntent();
        relativeLayout = findViewById(R.id.relative);
        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        String URL = "http://10.0.2.2:8000/assessment/?format=json";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest objectRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("Rest Response", response.toString());

                        //Getting assessment name to display

                        try{
                            for (int i = 0; i < response.length(); i++) {
                                btn = new Button(ViewAssessment.this);
                                btn.setLayoutParams(new LinearLayout.LayoutParams(600, 150));
                                btn.setId(i);
                                root = response.optJSONObject(i);
                                    System.out.println(root.getString("assesment_title"));
                                    btn.setText(root.getString("assesment_title"));

                                    relativeLayout.addView(btn);
                                    System.out.println("Baby");
                                    btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            openExam();
                                        }

                            });

                        }}
                        catch(Exception e) {
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
    public void openExam(){
            Intent intent = new Intent(this, Exam.class);
            startActivity(intent);
        }
}
