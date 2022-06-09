package com.example.unofinal;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.unofinal.backend.Data;


//TODO: comment all of the classes used
//TODO: see if I can spice up win screen

/* The MainActivity screen is the launching screen and it features a
short card animation along with options to start off the game. It includes radio
buttons and a checkbox instead of a text Input as a failsafe for user input (along with
an error message that pops up)
 */
public class MainActivity extends AppCompatActivity {

    //Error message
    TextView error;

    //Data storage class
    Data data;

    //Radio button and group for amt of players
    RadioGroup radioGroup;
    RadioButton radioButton;

    //Checkbox to add bot
    CheckBox bot;

    //Stores the multiple image view of the uno card logo (growing in size)
    ImageView unoCard, uno1Card, uno2Card, uno3Card, uno4Card, uno5Card, uno6Card, uno7Card;
    ImageView[] images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Initializing all the data object, imageViews, checkbox, textView and radiogroup
        data = new Data();

        unoCard = findViewById(R.id.uno);
        uno1Card = findViewById(R.id.uno1);
        uno2Card = findViewById(R.id.uno2);
        uno3Card = findViewById(R.id.uno3);
        uno4Card = findViewById(R.id.uno4);
        uno5Card = findViewById(R.id.uno5);
        uno6Card = findViewById(R.id.uno6);
        uno7Card = findViewById(R.id.uno7);

        images = new ImageView[]{unoCard, uno1Card, uno2Card, uno3Card, uno4Card, uno5Card, uno6Card, uno7Card};

        error = findViewById(R.id.errorMessage);
        radioGroup = findViewById(R.id.radioGroup);
        bot = findViewById(R.id.bot);


        //Starting background music and setting it to looping
        data.backgroundMusic = MediaPlayer.create(MainActivity.this, R.raw.sleepycat);
        data.backgroundMusic.setLooping(true);
        data.backgroundMusic.start();

        //setting error text to invisible
        error.setAlpha(0.0f);

        //Setting all of the images to invisible
        for (int i = 0; i < images.length; i++) {
            images[i].setVisibility(View.INVISIBLE);
        }

        //Method for starting animation
        visibility();

    }

    //Sets all ImageView of variable sizes visible at time increments
    public void visibility() {

        //Handler allows a repeated run of the code at a time interval
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            //To keep track of how many times the handler tuns
            int counter = 0;

            @Override
            public void run() {

                //Sets images visible 8 times
                if (counter <= 7) {
                    images[counter].setVisibility(View.VISIBLE);
                    counter++;
                    handler.postDelayed(this, 150);

                    //Exits handler
                } else {
                    handler.removeCallbacksAndMessages(null);
                }
            }
        }, 150);

    }


    //Play button click
    public void onBtnClick(View view) {

        //String to store the amount of players that will be playing
        String amtPlayers = "";

        //Takes radiogroup and checks which radiobutton is selected
        int clickedRadioButton = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(clickedRadioButton);

        //If nothing is selected then make the error text visible
        if (clickedRadioButton == -1) {
            error.setAlpha(1.0f);

            //Otherwise get the text from the radiobutton and assign it to the player that will play
        } else {
            amtPlayers = (String) radioButton.getText();
        }

        //If the checkbox for a bot is checked then assign data.bot to true (used in CardTestHorizontal)
        if (bot.isChecked()) {
            data.bot = true;
        }

        //If amtPlayers holds a value then
        if (!(amtPlayers.isEmpty())) {

            //Navigate to the CardTestHorizontal Activity while passing amtPlayers to CardTestHorizontal
            Intent intent = new Intent(MainActivity.this, Card.class);
            intent.putExtra("Amt Players", amtPlayers);
            startActivity(intent);

            //Plays card shuffle sound effect
            MediaPlayer backgroundMusic = MediaPlayer.create(MainActivity.this, R.raw.cardshuffle);
            backgroundMusic.start();
        }

    }


    //If the help button is clicked then navigate to the help menu
    public void HelpBtnClick(View view) {
        Intent intent = new Intent(MainActivity.this, Help.class);
        startActivity(intent);
    }
}

