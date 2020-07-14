package com.example.quote_on_quote;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button playButton = findViewById(R.id.playbutton);
        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.game_play_main);
            }
        });
    }

    //TODO: seed my database with 20 question objects.
    //TODO: make calls to my database to generate the set of questions the player will answer.


//    public int countCorrect(){
//        //this will count how many questions the player got correct and return the count
//    }

    public void startNewGame(){
        /*
        this method will contain all the logic that is needed when a new game starts
        TODO: make a collection of questions that live in an ArrayList (5 questions per game)
        TODO: keep track of what questions is currently being displayed
        */

    }


//    public String gameOver(int numberCorrect, int numberOfQuestions){
//        // this will output some type of message  to the player that tells them if they won or lost
//    }

}