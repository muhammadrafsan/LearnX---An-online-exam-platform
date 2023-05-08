package com.example.learnxandroid;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
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
import android.speech.tts.TextToSpeech;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class Viva extends AppCompatActivity {
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    ArrayList<String> eachOption = new ArrayList<String>();
    ArrayList<String> mQuestions = new ArrayList<String>();
    ArrayList<ArrayList<String>> mChoices = new ArrayList<ArrayList<String>>();
    ArrayList<String> mCorrectAnswers = new ArrayList<String>();
    ArrayList<Double> mark = new ArrayList<Double>();
    JSONObject root;
    TextToSpeech textToSpeech;
    Button btnText, nextBtn, recordBtn;
    TextView answerText;
    int index = 0;
    int mQuestionNumber = 0;
    String URL5 = "http://10.0.2.2:8000/saveviva/?format=json";
    String URL100 = "http://10.0.2.2:8000/saveexam/?format=json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viva);
        btnText = findViewById(R.id.listen);
        nextBtn = findViewById(R.id.next);
        recordBtn = findViewById(R.id.record);
        answerText = findViewById(R.id.answer);
        light();
        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent
                        = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
                } catch (Exception e) {
                    Toast
                            .makeText(Viva.this, " " + e.getMessage(),
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                // if No error is found then only it will run
                if(i!=TextToSpeech.ERROR){
                    // To Choose language of speech
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });

        // Adding OnClickListener
        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Haihai");
                System.out.println(mQuestions.get(mQuestionNumber).toString());
                textToSpeech.speak(mQuestions.get(mQuestionNumber).toString(),TextToSpeech.QUEUE_FLUSH,null);
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateQuestion();
                switch (view.getId()) {
                    case R.id.next:
                        //POSTING data in REST API
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL100,
                                response -> Toast.makeText(Viva.this, "Success", Toast.LENGTH_LONG).show(),
                                error -> Toast.makeText(Viva.this, "Error", Toast.LENGTH_LONG).show()
                        ) {
                            //Add parameters to the request
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("question", mQuestions.get(index).toString());
//                                params.put("optionA", null);
//                                params.put("optionB", null);
//                                params.put("optionC", null);
//                                params.put("optionD", null);

                                params.put("selectedAns", answerText.getText().toString());
                                params.put("assessmentTitle", "Demo Assessment");
                                params.put("quesType", "viva");
                                index++;
                                // params.put("email", personEmail);
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(Viva.this);
                        requestQueue.add(stringRequest);
                        break;
                }
            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                answerText.setText(
                        Objects.requireNonNull(result).get(0));
            }
        }
    }
    public void light(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        System.out.println("So light");
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
                                mQuestions.add(root.getString("vivaQuestion"));
                                mark.add(root.getDouble("vivaMark"));
                                mCorrectAnswers.add(root.getString("vivaCorrect"));

                                System.out.println("Correct: "+mCorrectAnswers);

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
    private void updateQuestion(){
        System.out.println("NEw: "+mQuestions.size());
        mQuestionNumber++;
        if(mQuestionNumber<mQuestions.size()) {
            btnText.setText("Listen Qs. "+mQuestionNumber);
//            mButtonChoice1.setText(getChoice1(mQuestionNumber));
//            mButtonChoice2.setText(getChoice2(mQuestionNumber));
//            mButtonChoice3.setText(getChoice3(mQuestionNumber));
//            mButtonChoice4.setText(getChoice4(mQuestionNumber));
//            mAnswer = getCorrectAnswer(0);

        }
        else
        {
            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
        }
    }
}
