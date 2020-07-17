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

    public static ArrayList<Question> createAllQuestions(){
      final ArrayList<Question> allQuestions = new ArrayList();

      OkHttpClient client = new OkHttpClient();
      String fireBaseUrl = "https://quoteonquote-capstone.firebaseio.com/questions.json";

      Request request = new Request.Builder()
          .url(fireBaseUrl)
          .build();
      client.newCall(request).enqueue(new Callback() {
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

              for(int i = 0;i < myResponse.length(); i++){
                JSONObject questionObject = myResponse.getJSONObject(i);
                JSONArray jsonAnswer = questionObject.getJSONArray("answerArray");
                String[] answerArray = new String[4];
                for(i = 0; i < 5; i++){
                  answerArray[i] = jsonAnswer.get(i).toString();
                }
                int correctAnswer = Integer.parseInt(questionObject.get("correctAnswer").toString());
  //              int id = Integer.parseInt(questionObject.get("id").toString());
                String imageName = questionObject.getString("imageName");
                String questionText = questionObject.getString("questionText");
                String quoteText = questionObject.getString("quoteText");
                Question question = new Question(quoteText, questionText, answerArray, correctAnswer, imageName);
                allQuestions.add(question);
              }

            } catch (JSONException e) {
              e.printStackTrace();
            }


          }
        }
      });
      return allQuestions;
    }

    public static Question[] getGameQuestions(){
      ArrayList<Question> allGameQuestions = createAllQuestions();
      Question[] gameQuestions = new Question[5];

      Random random = new Random();
      for(int i = 1; i <= 5; i++){
        Question newQuestion = allGameQuestions.get(random.nextInt(allGameQuestions.size()));
        gameQuestions[i] = newQuestion;
        allGameQuestions.remove(newQuestion);
      }
      return gameQuestions;
    }
}
