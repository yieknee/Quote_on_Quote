package com.example.quote_on_quote;


import android.widget.Button;
import android.widget.ImageView;
import android.view.View;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Question {
    int correctAnswer; // references the index of the correct answer in the answerArray
    String imageName;
    String questionText;
    String quoteText;
    String[] answerArray;
    int playerAnswer;

    public Question(String quote, String question, String[] answers, int correctAnswerIndex, String imageNameText ){
      correctAnswer = correctAnswerIndex;
      imageName = imageNameText;
      questionText = question;
      answerArray = answers;
      quoteText =  quote;
      playerAnswer = -1;
    }
    // checks if the answer the player chose is the right answer.
    public boolean isCorrect(){
      if(playerAnswer == correctAnswer){
        return true;
      } else {
        return false;
      }
    }

}
