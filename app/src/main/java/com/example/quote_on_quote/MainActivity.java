package com.example.quote_on_quote;

import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    ArrayList<Question> gameQuestions;
    int totalCorrectAnswers;
    int totalQuestions;

    ImageView quoteImageView;
    TextView questionTextView;
    Button playButton;
    Button answer0Button;
    Button answer1Button;
    Button answer2Button;
    Button answer3Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page_main);

        /*(6) Had to set the play button by default to be unclickable until all the questions for the game get loaded.
        Visually signalled the play button being inactive by making the color gray.
        ** see landing_page_main xml file
         */
        playButton = findViewById(R.id.playbutton);

        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.game_play_main);
                quoteImageView = findViewById(R.id.quoteimg);
                questionTextView = findViewById(R.id.question);
                answer0Button = findViewById(R.id.answer0);
                answer1Button = findViewById(R.id.answer1);
                answer2Button = findViewById(R.id.answer2);
                answer3Button = findViewById(R.id.answer3);
                startNewGame();
            }
        });

        /* (7) The below creates a new QuestionLookup instance and calls the createAllQuestions which triggers
           everything we did in that class.
           (8) the runnable that is  passed inside createAllQuestions gets the 5 questions needed for 1 round of the game.
           (9) If the questions have all been loaded the playButton gets enabled for the user to play the game and the color gets set to the theme
           (10) The app kept crashing becuase a UI element has to be changed and run on the UI thread, so we had to wrap the changes to the button in
           a  MainActivity.this.runOnUiThread to make the changes run on that thread.

         */
        QuestionLookup questionLookup = new QuestionLookup();
        questionLookup.createAllQuestions(()->{
           gameQuestions =  questionLookup.getGameQuestions(); //(8)
           MainActivity.this.runOnUiThread(() ->{
               playButton.setEnabled(true); //(9)
               playButton.setBackgroundColor(0xff339999);
           });

        });


    }

   public void renderQuestion(Question question){
        String imageText = question.imageName;
        int imageId = this.getResources().getIdentifier(imageText, "drawable", this.getPackageName());

        quoteImageView.setImageResource(imageId);
        questionTextView.setText(question.questionText);
        answer0Button.setText(question.answerArray[0]);
        answer1Button.setText(question.answerArray[1]);
        answer2Button.setText(question.answerArray[2]);
        answer3Button.setText(question.answerArray[3]);

   }
//
   public Question getCurrentQuestion(){
       Question question = null;
       if(gameQuestions.size() > 0){
            question = gameQuestions.get(0);
            gameQuestions.remove(0);
        };
       return question;
   }



//    public int countCorrect(){
//        //this will count how many questions the player got correct and return the count
//    }

    public void startNewGame(){
        /*
        this method will contain all the logic that is needed when a new game starts
        TODO: make a collection of questions that live in an ArrayList (5 questions per game)
        TODO: keep track of what questions is currently being displayed
        */



        Question firstQuestion = getCurrentQuestion();
        totalCorrectAnswers = 0;
        totalQuestions = gameQuestions.size();

        renderQuestion(firstQuestion);

    }


//    public String gameOver(int numberCorrect, int numberOfQuestions){
//        // this will output some type of message  to the player that tells them if they won or lost
//    }

}