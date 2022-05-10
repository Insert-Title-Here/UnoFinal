package com.example.unofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class CardTestHorizontal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_test_horizontal);

        LayoutInflater mylayoutInflator = getLayoutInflater();
        View view1 = mylayoutInflator.inflate(R.layout.card, null);
        ImageView image = (ImageView) view1.findViewById(R.id.cardImage);
        image.setImageResource(R.drawable.drawfour);
        Toast toast = new Toast(getApplicationContext());
        //toast.setView(view1);
        //toast.setDuration(Toast.LENGTH_LONG);
        //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);


    }
}