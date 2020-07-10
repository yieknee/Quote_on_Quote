package com.example.quote_on_quote;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //TODO: seed my database with 20 question objects.
    //TODO: make calls to my database to generate the set of questions the player will answer.


    public int countCorrect(){
        //this will count how many questions the player got correct and return the count
    }

    public String gameOver(int numberCOrrect, int numberOfQuestions){
        // this will output some type of message  to the player that tells them if they won or lost
    }

}