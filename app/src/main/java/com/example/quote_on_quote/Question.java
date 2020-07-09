package com.example.quote_on_quote;

public class Question {
    int correctAnswer;
    int playerAnswer;
    int imageId;
    String questionText;
    String[] answerArray;

    public Question(String question, String[] answers, int correctAnswerIndex, int imageIdNum ){
      correctAnswer = correctAnswerIndex;
      playerAnswer = -1;
      imageId = imageIdNum;
      questionText = question;
      answerArray = answers;
    }
}
