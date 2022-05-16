package com.example.unofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.unofinal.backend.Data;
import com.example.unofinal.backend.MainCard;

import java.util.ArrayList;
import java.util.List;

public class ColorChange extends AppCompatActivity {

    Data data = new Data();
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_change);


        ListView lv = findViewById(R.id.color);
        //TextView tv = findViewById(R.id.listText);

        list = new ArrayList<>();
        list.add("Yellow");
        list.add("Green");
        list.add("Red");
        list.add("Blue");

        ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);


        lv.setAdapter(adapter);

        listButtonListener(lv, list);


    }

    private void listButtonListener(ListView lv, List<String> list){

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == 0){
                    //data.previousCard = new MainCard(MainCard.Color.YELLOW, null);
                    data.discard.push(new MainCard(MainCard.Color.YELLOW, null));


                    //tv.setText(list.get(0));

                }else if(position == 1){
                    //data.previousCard = new MainCard(MainCard.Color.GREEN, null);
                    data.discard.push(new MainCard(MainCard.Color.GREEN, null));


                    //tv.setText(list.get(1));


                }else if(position == 2){
                    //data.previousCard = new MainCard(MainCard.Color.RED, null);
                    data.discard.push(new MainCard(MainCard.Color.RED, null));


                    //tv.setText(list.get(2));


                }else {
                    //data.previousCard = new MainCard(MainCard.Color.BLUE, null);
                    data.discard.push(new MainCard(MainCard.Color.BLUE, null));


                    //tv.setText(list.get(3));
                }


                System.out.println("Stuff");


                finish();
                data.change = true;

            }
        });
    }

}