package com.example.quote_on_quote;

public class Question {
    int correctAnswer; // references the index of the correct answer in the answerArray
    int playerAnswer;
    int imageId;
    String questionText;
    String[] answerArray;

    public Question(String question, String[] answers, int correctAnswerIndex, int imageIdNum ){
      correctAnswer = correctAnswerIndex;
      playerAnswer = -10; //this will be reset to the index of the answer the player chooses.
      imageId = imageIdNum;
      questionText = question;
      answerArray = answers;
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
