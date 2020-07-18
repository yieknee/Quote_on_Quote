package com.example.quote_on_quote;

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
