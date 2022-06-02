package com.example.unofinal;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.unofinal.backend.Data;

public class MainActivity extends AppCompatActivity {

    TextView error;
    Data data;
    ImageView unoCard, uno1Card, uno2Card, uno3Card, uno4Card, uno5Card, uno6Card, uno7Card;
    RadioGroup radioGroup;
    RadioButton radioButton;

    //TODO: Fix music stuff (doesn't play sometimes)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        radioGroup=findViewById(R.id.radioGroup);





        //TODO: Experiment with stopping music from media player
        data = new Data();

        unoCard = findViewById(R.id.uno);
        uno1Card = findViewById(R.id.uno1);
        uno2Card = findViewById(R.id.uno2);
        uno3Card = findViewById(R.id.uno3);
        uno4Card = findViewById(R.id.uno4);
        uno5Card = findViewById(R.id.uno5);
        uno6Card = findViewById(R.id.uno6);
        uno7Card = findViewById(R.id.uno7);


        ImageView[] images = {unoCard, uno1Card, uno2Card, uno3Card, uno4Card, uno5Card, uno6Card, uno7Card};


        for(int i = 0; i < images.length; i++){
            images[i].setVisibility(View.INVISIBLE);
        }







                //MediaPlayer backgroundMusic = MediaPlayer.create(MainActivity.this, R.raw.sleepycat);
        data.backgroundMusic = MediaPlayer.create(MainActivity.this, R.raw.sleepycat);

        data.backgroundMusic.setLooping(true);
        data.backgroundMusic.start();



        error = findViewById(R.id.errorMessage);

        error.setAlpha(0.0f);

        //visiblity(images);

    }






    public void visiblity(ImageView[] images){


        int counter = 0;
        long startTime = System.currentTimeMillis();

        while(images[images.length - 1].getVisibility() == View.INVISIBLE){
            if(System.currentTimeMillis() - startTime == 1000){
                System.out.println(counter);
                images[counter].setVisibility(View.VISIBLE);
                startTime = System.currentTimeMillis();
                counter++;


            }
        }
    }

    public void onBtnClick(View view){
        //Button button = findViewById(R.id.playButton);
        EditText players = findViewById(R.id.playerNumber);

        String amtPlayers = "";
                //= players.getText().toString();

        //int tempamtPlayers = Integer.parseInt(amtPlayers);

        int clickedRadioButton=radioGroup.getCheckedRadioButtonId();
        radioButton=findViewById(clickedRadioButton);
        if(clickedRadioButton ==-1){
            error.setAlpha(1.0f);
        } else {
            amtPlayers = (String)radioButton.getText();
        }


/*
        if(tempamtPlayers <= 0){
            amtPlayers = "2";
        }else if(tempamtPlayers >= 6){
            amtPlayers = "5";
        }

 */




        if(!(/*players.getText().toString().isEmpty()*/ amtPlayers.isEmpty())) {
            Intent intent = new Intent(MainActivity.this, CardTestHorizontal.class);
            intent.putExtra("Amt Players", amtPlayers);
            startActivity(intent);


            MediaPlayer backgroundMusic = MediaPlayer.create(MainActivity.this, R.raw.cardshuffle);
            backgroundMusic.start();
        }

        /*else{
            //players.setHint("Please enter the number of players");
            error.setAlpha(1.0f);


        }

         */
    }


    public void HelpBtnClick(View view){
        Intent intent = new Intent(MainActivity.this, Help.class);
        //intent.putExtra("Amt Players", "2");

        //MediaPlayer backgroundMusic = MediaPlayer.create(MainActivity.this, R.raw.cardshuffle);
        //backgroundMusic.start();

        startActivity(intent);
    }


}