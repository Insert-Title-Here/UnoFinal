package com.example.unofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

/* The Help screen is the screen that can be navigated from multiple
screens. The screen features some tips for the playthrough and a description
of some unique features of this version of UNO.
 */
public class Help extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);



    }

    //Goes back to previous activity
    public void back(View v){
        finish();
    }
}