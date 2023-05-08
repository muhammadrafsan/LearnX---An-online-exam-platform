package com.example.learnxandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    TextView  emails, name;
    Button signout, data;
    String personEmail ;
    String usernaam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //MainActivity2 ob = new MainActivity2();
        //System.out.println("Good "+personEmail);

        String URL2 = "http://10.0.2.2:8000/user/?format=json";
        RequestQueue requestQueue2 = Volley.newRequestQueue(this);
        //GETTING data in REST API
        JsonArrayRequest objectRequest2 = new JsonArrayRequest(
                Request.Method.GET,
                URL2,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("Rest Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Response", error.toString());
                    }
                }
        );
        requestQueue2.add(objectRequest2);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //name = findViewById(R.id.textName);
        emails = findViewById(R.id.textEmail);
        System.out.println("FIRST EMAIL: "+emails);
        name = findViewById(R.id.textName);
        data = findViewById(R.id.button3);
        signout = findViewById(R.id.button);
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                case R.id.button3:
                    //POSTING data in REST API
                String url3 = "http://10.0.2.2:8000/user/";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url3,
                        response -> Toast.makeText(MainActivity2.this, "Success", Toast.LENGTH_LONG).show(),
                        error -> Toast.makeText(MainActivity2.this, "Error", Toast.LENGTH_LONG).show()
                ){
                    //Add parameters to the request
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        System.out.println("Hurrah "+personEmail);
                        System.out.println(usernaam);
                        params.put("username", usernaam);
                        params.put("email", personEmail);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
                requestQueue.add(stringRequest);
                break;
            }
            openViewCohort();
            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button:
                        signOut();
                        break;
                }
            }
        });
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String firstname = acct.getGivenName().toLowerCase();
            usernaam = firstname.replace(' ', '_');

            personEmail = acct.getEmail();
            //personEmail = new String(acct.getEmail());
            String personName = acct.getDisplayName();
            //System.out.println("Agey"+personEmail);
            //usrnm.setText(personName);
            emails.setText(personEmail);
            //System.out.println();
            name.setText(personName);
            System.out.println("BAD "+userEmail());
        }
    }

    public void openViewCohort() {
        Intent intent = new Intent(this, ViewCohort.class);
        startActivity(intent);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity2.this, "Signed Out!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });

    }
    public String userEmail()
    {
        return personEmail;
    }
}