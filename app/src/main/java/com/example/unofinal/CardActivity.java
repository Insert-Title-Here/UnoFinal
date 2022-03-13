package com.example.unofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CardActivity extends AppCompatActivity {

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);


        String cardNames[] = {"Seven", "Not Seven", "Totally a Seven", "Why is this even a seven"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cardNames);

        lv = findViewById(R.id.cardList);

        lv.setAdapter(adapter);
    }
}