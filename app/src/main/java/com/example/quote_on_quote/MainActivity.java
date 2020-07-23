package com.example.quote_on_quote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

  ArrayList<Question> gameQuestions;
  int totalCorrectAnswers = 0;
  int totalQuestions;
  int playerAnswer;
  int currentQuestionIndex = 0;
  int correctAnswerButtonId;
  int numClick = 0;
  String gameOverMessage;

  ImageView quoteImageView;
  ImageView goImage;
  TextView questionTextView;
  TextView answerResultText;
  TextView goMessage;
  Button playButton;
  Button answer0Button;
  Button answer1Button;
  Button answer2Button;
  Button answer3Button;
  Button submitButton;
  Button moreInfoButton;
  Button nextQuestionButton;
  Button correctAnswerButton;
  Button leave;
  Button playAgain;
  LinearLayout buttonHolder;


  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.music); //Royalty Free Music from Bensound.com
    mediaPlayer.start();
    setContentView(R.layout.landing_page_main);

        /*(6) Had to set the play button by default to be unclickable until all the questions for the game get loaded.
        Visually signalled the play button being inactive by making the color gray.
        ** see landing_page_main xml file
         */
    playButton = findViewById(R.id.playbutton);

    playButton.setOnClickListener(v -> {
      setContentView(R.layout.game_play_main);
      quoteImageView = findViewById(R.id.quoteimg);
      questionTextView = findViewById(R.id.question);
      answerResultText = findViewById(R.id.answerresult);
      answer0Button = findViewById(R.id.answer0);
      answer1Button = findViewById(R.id.answer1);
      answer2Button = findViewById(R.id.answer2);
      answer3Button = findViewById(R.id.answer3);
      submitButton = findViewById(R.id.submit);
      moreInfoButton = findViewById(R.id.moreinfo);
      nextQuestionButton = findViewById(R.id.nextquestion);
      buttonHolder = findViewById(R.id.buttonholder);


      startNewGame();

      answer0Button.setOnClickListener(v1 -> {
        Question currentQuestion = getCurrentQuestion();
        playerAnswer = 0;
        setPlayerAnswer(currentQuestion, answer0Button, 0);

        answer1Button.setText(currentQuestion.answerArray[1]);
        answer2Button.setText(currentQuestion.answerArray[2]);
        answer3Button.setText(currentQuestion.answerArray[3]);
        submitButton.setEnabled(true);
      });

      answer1Button.setOnClickListener(v12 -> {
        Question currentQuestion = getCurrentQuestion();

        playerAnswer = 1;
        setPlayerAnswer(currentQuestion, answer1Button, 1);

        answer0Button.setText(currentQuestion.answerArray[0]);
        answer2Button.setText(currentQuestion.answerArray[2]);
        answer3Button.setText(currentQuestion.answerArray[3]);
        submitButton.setEnabled(true);
      });

      answer2Button.setOnClickListener(v13 -> {
        Question currentQuestion = getCurrentQuestion();
        playerAnswer = 2;
        setPlayerAnswer(currentQuestion, answer2Button, 2);

        answer0Button.setText(currentQuestion.answerArray[0]);
        answer1Button.setText(currentQuestion.answerArray[1]);
        answer3Button.setText(currentQuestion.answerArray[3]);
        submitButton.setEnabled(true);
      });

      answer3Button.setOnClickListener(v14 -> {
        Question currentQuestion = getCurrentQuestion();
        playerAnswer = 3;
        setPlayerAnswer(currentQuestion, answer3Button, 3);

        answer0Button.setText(currentQuestion.answerArray[0]);
        answer1Button.setText(currentQuestion.answerArray[1]);
        answer2Button.setText(currentQuestion.answerArray[2]);
        submitButton.setEnabled(true);
      });

      submitButton.setOnClickListener(view -> {
        setCorrectAnswerButtonId();

        Question currentQuestion = getCurrentQuestion();
        submitButton.setVisibility(View.GONE); //hide the submit button

        if (currentQuestion.isCorrect()) {
          countCorrect(true); //increment totalCorrectAnswers
          quoteImageView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce));
          quoteImageView.setImageResource(R.drawable.correct);
        } else {
          quoteImageView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce));
          quoteImageView.setImageResource(R.drawable.wrong);
        }
        String text = "\u2714" + currentQuestion.answerArray[currentQuestion.correctAnswer];
        correctAnswerButton.setText(text);
        buttonHolder.setVisibility(View.VISIBLE);
      });

      moreInfoButton.setOnClickListener(view -> {
        Question currentQuestion = getCurrentQuestion();
        if (numClick == 0) {
          answerResultText.setText(currentQuestion.infoText);
          answerResultText.setMovementMethod(new ScrollingMovementMethod());

          questionTextView.setVisibility(View.GONE);
          quoteImageView.clearAnimation();
          quoteImageView.setVisibility(View.GONE);
          answer0Button.setVisibility(View.GONE);
          answer1Button.setVisibility(View.GONE);
          answer2Button.setVisibility(View.GONE);
          answer3Button.setVisibility(View.GONE);
          answerResultText.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein));
          answerResultText.setVisibility(View.VISIBLE);
          numClick++;
        } else {
          answerResultText.setVisibility(View.GONE);
          answerResultText.clearAnimation();
          questionTextView.setVisibility(View.VISIBLE);
          quoteImageView.setImageResource(getResources().getIdentifier(currentQuestion.imageName, "drawable", getPackageName()));
          quoteImageView.setVisibility(View.VISIBLE);
          answer0Button.setVisibility(View.VISIBLE);
          answer1Button.setVisibility(View.VISIBLE);
          answer2Button.setVisibility(View.VISIBLE);
          answer3Button.setVisibility(View.VISIBLE);
          numClick = 0;
        }

      });

      nextQuestionButton.setOnClickListener(view -> {
        if (currentQuestionIndex < 4) {
          numClick = 0;
          answerResultText.setVisibility(View.GONE);
          buttonHolder.setVisibility(View.GONE);

          pickQuestion();
          renderQuestion();
        } else {
          gameOver(totalCorrectAnswers, totalQuestions);
          setContentView(R.layout.game_over_main);

          TextView goText = findViewById(R.id.gotext);
          goText.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slidedown));

          goMessage = findViewById(R.id.gomessage);
          goMessage.setText(gameOverMessage);

          goImage = findViewById(R.id.goimg);
          if (totalCorrectAnswers == totalQuestions) {
            goImage.setImageResource(R.drawable.win);
          } else {
            goImage.setImageResource(R.drawable.lose);
          }
          goImage.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink));
          leave = findViewById(R.id.leave);
          leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mediaPlayer.stop();
              finish();
            }
          });

          playAgain = findViewById(R.id.playagain);
          playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mediaPlayer.stop();
              //got the code below from: https://stackoverflow.com/questions/13956026/re-launch-android-application-programmatically
              Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(
                  getBaseContext().getPackageName());
              assert intent != null;
              intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
              intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
              startActivity(intent);
            }
          });

        }
      });

    });

        /* (7) The below creates a new QuestionLookup instance and calls the createAllQuestions which triggers
           everything we did in that class.
           (8) the runnable that is  passed inside createAllQuestions gets the 5 questions needed for 1 round of the game.
           (9) If the questions have all been loaded the playButton gets enabled for the user to play the game and the color gets set to the theme
           (10) The app kept crashing becuase a UI element has to be changed and run on the UI thread, so we had to wrap the changes to the button in
           a  MainActivity.this.runOnUiThread to make the changes run on that thread.
         */
    QuestionLookup questionLookup = new QuestionLookup();
    questionLookup.createAllQuestions(() -> {
      gameQuestions = questionLookup.getGameQuestions(); //(8)
      MainActivity.this.runOnUiThread(() -> {
        playButton.setEnabled(true); //(9)
        playButton.setBackgroundColor(0xff339999);
      });
    });


  }

  public void renderQuestion() {
    Question question = getCurrentQuestion();
    String imageText = question.imageName;
    int imageId = this.getResources().getIdentifier(imageText, "drawable", this.getPackageName()); //found here: https://stackoverflow.com/questions/3476430/how-to-get-a-resource-id-with-a-known-resource-name
    quoteImageView.setImageResource(imageId);
    quoteImageView.clearAnimation();
    answerResultText.clearAnimation();
    questionTextView.setText(question.questionText);

    answer0Button.setText(question.answerArray[0]);
    answer1Button.setText(question.answerArray[1]);
    answer2Button.setText(question.answerArray[2]);
    answer3Button.setText(question.answerArray[3]);

    if (currentQuestionIndex > 0) {
      questionTextView.setVisibility(View.VISIBLE);
      quoteImageView.setVisibility(View.VISIBLE);
      answer0Button.setVisibility(View.VISIBLE);
      answer1Button.setVisibility(View.VISIBLE);
      answer2Button.setVisibility(View.VISIBLE);
      answer3Button.setVisibility(View.VISIBLE);
      submitButton.setVisibility(View.VISIBLE);
    }
    submitButton.setEnabled(false);
  }

  public void pickQuestion() {
    currentQuestionIndex += 1;
  }

  public void setCorrectAnswerButtonId() {
    Question chosenQuestion = gameQuestions.get(currentQuestionIndex); // picks current question
    String correctAnswerButtonName = "answer" + chosenQuestion.correctAnswer; //uses current question to find the name of the correct answer button
    correctAnswerButtonId = getResources().getIdentifier(correctAnswerButtonName, "id", getPackageName()); // gets id of correctAnswerButton
    correctAnswerButton = findViewById(correctAnswerButtonId);
  }

  public Question getCurrentQuestion() {
    Question question = null;
    if (gameQuestions.size() > 0) {
      question = gameQuestions.get(currentQuestionIndex);
    }
    return question;
  }

  public void setPlayerAnswer(Question currentQuestion, Button clickedButton, int answerInt) {
    currentQuestion.playerAnswer = playerAnswer;
    String text = "\uD83D\uDCAC" + currentQuestion.answerArray[answerInt];
    clickedButton.setText(text);
    // "\uD83D\uDCAC" **speech bubble java unicode
  }

  public void countCorrect(Boolean result) {
    //this will count how many questions the player got correct and return the count
    if (result) {
      totalCorrectAnswers += 1;
    }
  }

  public void startNewGame() {
    totalCorrectAnswers = 0;
    totalQuestions = gameQuestions.size();
    renderQuestion();
  }


  public void gameOver(int numberCorrect, int numberOfQuestions) {
    // this will output some type of message  to the player that tells them if they won or lost
    if (totalCorrectAnswers == totalQuestions) {
      gameOverMessage = "You got all " + totalQuestions + " right!";
    } else {
      gameOverMessage = "You got " + totalCorrectAnswers + "/" + totalQuestions + " right. \n Better luck next time!";
    }
  }

}