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

// (1)(2) **see lines 70-80
public class QuestionLookup implements Callback{
  //(4)
  Runnable afterLoaded;


  public ArrayList<Question> allQuestions = new ArrayList<>();
  //(3)
  @Override
  public void onFailure(Request request, IOException e) {
    e.printStackTrace();
  }
  //(3)
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
        }
      } catch (JSONException e) {
        e.printStackTrace();
      }

    }
    afterLoaded.run();
  }

  //(4)
  public ArrayList<Question> createAllQuestions(Runnable afterLoaded){
    //(4)
    this.afterLoaded = afterLoaded;

    OkHttpClient client = new OkHttpClient();
    String fireBaseUrl = "https://quoteonquote-capstone.firebaseio.com/questions.json";

    Request request = new Request.Builder()
        .url(fireBaseUrl)
        .build();
    //(5)
    client.newCall(request).enqueue(this);
     /*allQuestions would not populate (returned []) because OkHttp was making asynchronous calls and the return of all questions was happening before the array could be populated.
     To solve it we (Ansel the TA helped me) had to
     (1) separate these methods from the Question class and create this QuestionLookup class and
     (2) implement a callback within this class.
     (3) put the Callback request and response from okhttps call to firebase outside of createAllQuestions method
     (4) create a runnable called after loaded and pass the runnable into this method and set the runnable variable.
     (5) the this in the enque triggers the runnable and runs the callback methods which populate the allQuestions array.
     **see MainActivity.java file for the rest of the solution to this bug.
      */

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
