package com.example.quote_on_quote;
import java.util.Arrays;
import java.util.ArrayList;

public class Question {
    int correctAnswer; // references the index of the correct answer in the answerArray
    int imageId;
    String questionText;
    String quoteText;
    String[] answerArray;
    int playerAnswer;

    public Question(String quote, String question, String[] answers, int correctAnswerIndex, int imageIdNum ){
      correctAnswer = correctAnswerIndex;
      imageId = imageIdNum;
      questionText = question;
      answerArray = answers;
      quoteText =  quote;
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
