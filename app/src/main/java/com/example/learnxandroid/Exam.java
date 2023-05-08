package com.example.learnxandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Exam extends AppCompatActivity {
     //Question mQuestionLibrary = new Question();
//    String mQuestions [] = {
//             "This part of the plant absorbs energy from the sun.",
//             "This part of the plant attracts bees, butterflies and hummingbirds.",
//             "The _______ holds the plant upright."
//     };
    //String mQuestions[] = new String[5];
    ArrayList<String> eachOption = new ArrayList<String>();
    ArrayList<String> mQuestions = new ArrayList<String>();
    ArrayList<ArrayList<String>> mChoices = new ArrayList<ArrayList<String>>();
    ArrayList<String> mCorrectAnswers = new ArrayList<String>();
    ArrayList<Double> mark = new ArrayList<Double>();
    //int size = mQuestions.size();
    //String mChoices[][] = new String[][3];
    //String mCorrectAnswers[] = new String[size];
//    String mChoices[][] = {
//            {"Fruit", "Leaves", "Seeds"},
//            {"Bark", "Flower", "Roots"},
//            {"Flower", "Leaves", "Stem"}
//    };
//    String mCorrectAnswers[] = {"Roots", "Leaves", "Flower", "Stem"};
     TextView mScoreView;
     TextView mQuestionView;
     Button mButtonChoice1;
     Button mButtonChoice2;
     Button mButtonChoice3;
     Button mButtonChoice4;
    JSONObject root;
     String mAnswer;
     int increment = 0;
     double mScore = 0.0;
     int mQuestionNumber = 1;
     int index = 0;
    String URL100 = "http://10.0.2.2:8000/saveexam/?format=json";
    String URL5 = "http://10.0.2.2:8000/exammcq/?format=json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam);


        mScoreView = (TextView)findViewById(R.id.score);
        mQuestionView = (TextView)findViewById(R.id.question);
        mButtonChoice1 = (Button)findViewById(R.id.choice1);
        mButtonChoice2 = (Button)findViewById(R.id.choice2);
        mButtonChoice3 = (Button)findViewById(R.id.choice3);
        mButtonChoice4 = (Button)findViewById(R.id.choice4);
        System.out.println("Opening");
        heavy();
        //updateQuestion();

        //Start of Button Listener for Button1
//        mButtonChoice1.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                //My logic for Button goes in here
//
//                if (mButtonChoice1.getText().equals(mCorrectAnswers.get(increment))){
//                    mScore = mScore + mark.get(increment);
//                    System.out.println("Total mark: "+mScore);
//                    updateScore(mScore);
//                    updateQuestion();
//
//                    //This line of code is optiona
//                    Toast.makeText(Exam.this, "correct", Toast.LENGTH_SHORT).show();
//
//                }else {
//                    Toast.makeText(Exam.this, "wrong", Toast.LENGTH_SHORT).show();
//                    System.out.println("Total mark: "+mScore);
//                    updateQuestion();
//                }}
//
//        });

        //End of Button Listener for Button1

        //Start of Button Listener for Button2
        mButtonChoice2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //My logic for Button goes in here
                System.out.println(mButtonChoice2.getText());
                if (mButtonChoice2.getText().equals(mCorrectAnswers.get(increment))){
                    mScore = mScore + mark.get(increment);
                    System.out.println("Total mark: "+mScore);
                    updateScore(mScore);
                    updateQuestion();
                    //This line of code is optiona
                    Toast.makeText(Exam.this, "correct", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(Exam.this, "wrong", Toast.LENGTH_SHORT).show();
                    System.out.println("Total mark: "+mScore);
                    updateQuestion();
                }
                switch (view.getId()) {
                    case R.id.choice2:
                        //POSTING data in REST API
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL100,
                                response -> Toast.makeText(Exam.this, "Success", Toast.LENGTH_LONG).show(),
                                error -> Toast.makeText(Exam.this, "Error", Toast.LENGTH_LONG).show()
                        ) {
                            //Add parameters to the request
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("question", mQuestions.get(index));
                                params.put("optionA", mChoices.get(index).get(0));
                                params.put("optionB", mChoices.get(index).get(1));
                                params.put("optionC", mChoices.get(index).get(2));
                                params.put("optionD", mChoices.get(index).get(3));
                                params.put("assessmentTitle", "Demo Assessment");
                                params.put("selectedAns", mChoices.get(index).get(1));
                                params.put("quesType", "mcq");
                                index++;
                                // params.put("email", personEmail);
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(Exam.this);
                        requestQueue.add(stringRequest);
                        break;
                }
            }

        });

        //End of Button Listener for Button2


        //Start of Button Listener for Button3
        mButtonChoice3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //My logic for Button goes in here

                    if (mButtonChoice3.getText().equals(mCorrectAnswers.get(increment))){
                        mScore = mScore + mark.get(increment);
                        System.out.println("Total mark: "+mScore);
                        updateScore(mScore);
                        updateQuestion();
                        System.out.println("Final mark: "+mScore);
                        //This line of code is optiona
                        Toast.makeText(Exam.this, "correct", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(Exam.this, "wrong", Toast.LENGTH_SHORT).show();
                        System.out.println("Total mark: "+mScore);
                        updateQuestion();
                    }

                switch (view.getId()) {
                    case R.id.choice3:
                        //POSTING data in REST API
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL100,
                                response -> Toast.makeText(Exam.this, "Success", Toast.LENGTH_LONG).show(),
                                error -> Toast.makeText(Exam.this, "Error", Toast.LENGTH_LONG).show()
                        ) {
                            //Add parameters to the request
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("question", mQuestions.get(index));
                                params.put("optionA", mChoices.get(index).get(0));
                                params.put("optionB", mChoices.get(index).get(1));
                                params.put("optionC", mChoices.get(index).get(2));
                                params.put("optionD", mChoices.get(index).get(3));
                                params.put("assessmentTitle", "Demo Assessment");
                                params.put("selectedAns", mChoices.get(index).get(2));
                                params.put("quesType", "mcq");
                                index++;
                                // params.put("email", personEmail);
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(Exam.this);
                        requestQueue.add(stringRequest);
                        break;
                }
                }


        });
        mButtonChoice4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //My logic for Button goes in here
                    if (mButtonChoice4.getText().equals(mCorrectAnswers.get(increment))){
                        mScore = mScore + mark.get(increment);
                        System.out.println("Total mark: "+mScore);
                        updateScore(mScore);
                        updateQuestion();
                        System.out.println(mScore);
                        //This line of code is optiona
                        Toast.makeText(Exam.this, "correct", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(Exam.this, "wrong", Toast.LENGTH_SHORT).show();
                        System.out.println("Total mark: "+mScore);
                        updateQuestion();
                    }

                switch (view.getId()) {
                    case R.id.choice4:
                        //POSTING data in REST API
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL100,
                                response -> Toast.makeText(Exam.this, "Success", Toast.LENGTH_LONG).show(),
                                error -> Toast.makeText(Exam.this, "Error", Toast.LENGTH_LONG).show()
                        ) {
                            //Add parameters to the request
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("question", mQuestions.get(index));
                                params.put("optionA", mChoices.get(index).get(0));
                                params.put("optionB", mChoices.get(index).get(1));
                                params.put("optionC", mChoices.get(index).get(2));
                                params.put("optionD", mChoices.get(index).get(3));
                                params.put("assessmentTitle", "Demo Assessment");
                                params.put("selectedAns", mChoices.get(index).get(3));
                                params.put("quesType", "mcq");
                                index++;
                                // params.put("email", personEmail);
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(Exam.this);
                        requestQueue.add(stringRequest);
                        break;
                }
            }
        });
        //End of Button Listener for Button3
        mButtonChoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mButtonChoice1.getText().equals(mCorrectAnswers.get(increment))){
                    mScore = mScore + mark.get(increment);
                    System.out.println("Total mark: "+mScore);
                    updateScore(mScore);
                    updateQuestion();

                    //This line of code is optiona
                    Toast.makeText(Exam.this, "correct", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(Exam.this, "wrong", Toast.LENGTH_SHORT).show();
                    System.out.println("Total mark: "+mScore);
                    updateQuestion();
                }

                switch (view.getId()) {
                    case R.id.choice1:
                        //POSTING data in REST API
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL100,
                                response -> Toast.makeText(Exam.this, "Success", Toast.LENGTH_LONG).show(),
                                error -> Toast.makeText(Exam.this, "Error", Toast.LENGTH_LONG).show()
                        ) {
                            //Add parameters to the request
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("question", mQuestions.get(index));
                                params.put("optionA", mChoices.get(index).get(0));
                                params.put("optionB", mChoices.get(index).get(1));
                                params.put("optionC", mChoices.get(index).get(2));
                                params.put("optionD", mChoices.get(index).get(3));
                                params.put("assessmentTitle", "Demo Assessment");
                                params.put("selectedAns", mChoices.get(index).get(0));
                                params.put("quesType", "mcq");

                                index++;
                               // params.put("email", personEmail);
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(Exam.this);
                        requestQueue.add(stringRequest);
                        break;
                }
            }
        });
    }
    public void heavy()
    {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        System.out.println("So heavy");
        JsonArrayRequest objReq = new JsonArrayRequest(
                Request.Method.GET,
                URL5,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("hello");
                        Log.e("Rest Response", response.toString());

                        System.out.println("Inside heavy");
                        try{
                            for (int i = 0; i < response.length(); i++) {
                                root = response.optJSONObject(i);

                                mQuestions.add(root.getString("question"));
//                                eachOption.add(root.getString("optionA"));
//                                eachOption.add(root.getString("optionB"));
//                                eachOption.add(root.getString("optionC"));
                                System.out.println(eachOption);
                               // mChoices.add(eachOption);

                                System.out.println("First question: "+mQuestions.get(i));
                                //mChoices[i][0]= root.getString("optionA");
                                System.out.println("ABCD");
                                mChoices.add(new ArrayList<String>());
                                mChoices.get(i).add(0, root.getString("optionA"));
                                mChoices.get(i).add(1, root.getString("optionB"));
                                mChoices.get(i).add(2, root.getString("optionC"));
                                mChoices.get(i).add(3, root.getString("optionD"));
                                mark.add(root.getDouble("marks"));
                                System.out.println("I is "+mChoices);
                                System.out.println("ABCD");
                                //System.out.println(mChoices[i][0]);
                                //mChoices[i][1]= root.getString("optionB");
                                //System.out.println(mChoices[i][1]);
                                //mChoices[i][2]= root.getString("optionC");

                                mCorrectAnswers.add(root.getString("correctAns"));
                                System.out.println("COrrect: "+mCorrectAnswers);
                                //System.out.println("Heavy");
                                mQuestionView.setText(getQuestion(0));
                                mButtonChoice1.setText(getChoice1(0));
                                mButtonChoice2.setText(getChoice2(0));
                                mButtonChoice3.setText(getChoice3(0));
                                mButtonChoice4.setText(getChoice4(0));
                                mAnswer = getCorrectAnswer(0);
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
        requestQueue.add(objReq);

    }
    public String getQuestion(int a) {
        String question = mQuestions.get(a);
        return question;
    }
    public String getChoice1(int a) {
        //String choice0 = mChoices[a][0];
        String choice0 = mChoices.get(a).get(0);
        return choice0;
    }
////
////
    public String getChoice2(int a) {
        //String choice1 = mChoices[a][1];
        String choice1 = mChoices.get(a).get(1);
        return choice1;
    }
////
    public String getChoice3(int a) {
        //String choice2 = mChoices[a][2];
        String choice2 = mChoices.get(a).get(2);
        return choice2;
    }
    public String getChoice4(int a) {
        //String choice2 = mChoices[a][2];
        String choice3 = mChoices.get(a).get(3);
        return choice3;
    }
////
    public String getCorrectAnswer(int a) {
        String answer = mCorrectAnswers.get(a);
        return answer;
    }

    private void updateQuestion(){
        System.out.println("NEw: "+mQuestions.size());
        if(mQuestionNumber<mQuestions.size()) {

            increment++;
            mQuestionView.setText(getQuestion(mQuestionNumber));
            mButtonChoice1.setText(getChoice1(mQuestionNumber));
            mButtonChoice2.setText(getChoice2(mQuestionNumber));
            mButtonChoice3.setText(getChoice3(mQuestionNumber));
            mButtonChoice4.setText(getChoice4(mQuestionNumber));
            mAnswer = getCorrectAnswer(0);
            mQuestionNumber++;
        }
        else
        {
            openViva();
        }
    }
    public void openViva(){
        Intent intent = new Intent(this, Viva.class);
        startActivity(intent);
    }

    void updateScore(double point) {
        mScoreView.setText("" + mScore);
    }
}
