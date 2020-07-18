package com.example.quote_on_quote;

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

public class QuestionLookup implements Callback{

  Runnable afterLoaded;

  public ArrayList<Question> allQuestions = new ArrayList<>();

  @Override
  public void onFailure(Request request, IOException e) {
    e.printStackTrace();
  }

  @Override
  public void onResponse(Response response) throws IOException {
    if(response.isSuccessful()){
      String firebaseResponse = response.body().string();
      try {
        JSONArray myResponse = new JSONArray(firebaseResponse);

        for(int i = 0; i < myResponse.length(); i++){
          JSONObject questionObject = myResponse.getJSONObject(i);
          JSONArray jsonAnswer = questionObject.getJSONArray("answerArray");
          String[] answerArray = new String[4];
          for(int j = 0; j < 4; j++){
            answerArray[j] = jsonAnswer.get(j).toString();
          }

          int correctAnswer = Integer.parseInt(questionObject.get("correctAnswer").toString());
          String imageName = questionObject.getString("imageName");
          String questionText = questionObject.getString("questionText");
          String quoteText = questionObject.getString("quoteText");
          Question question = new Question(quoteText, questionText, answerArray, correctAnswer,imageName);
          allQuestions.add(question);
          System.out.println(allQuestions);
        }
      } catch (JSONException e) {
        e.printStackTrace();
      }

    }
    afterLoaded.run();
  }

  public ArrayList<Question> createAllQuestions(Runnable afterLoaded){

    this.afterLoaded = afterLoaded;

    OkHttpClient client = new OkHttpClient();
    String fireBaseUrl = "https://quoteonquote-capstone.firebaseio.com/questions.json";

    Request request = new Request.Builder()
        .url(fireBaseUrl)
        .build();
    client.newCall(request).enqueue(this);
//      System.out.println(allQuestions);
    return allQuestions;
  }

  public ArrayList<Question> getGameQuestions(){
    ArrayList<Question> gameQuestions = new ArrayList<Question>();

    Random random = new Random();
    for(int i = 1; i <= 5; i++){
      Question newQuestion = allQuestions.get(random.nextInt(allQuestions.size()));
      gameQuestions.add(newQuestion);
      allQuestions.remove(newQuestion);
    }
    return gameQuestions;
  }
}
