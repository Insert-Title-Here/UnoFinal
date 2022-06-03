package com.example.unofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;


//TODO: add to help menu and maybe include screen shot (make the screen look better and maybe add bot instructions)
public class Help extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }



    }

    public void back(View v){
        finish();
    }
}