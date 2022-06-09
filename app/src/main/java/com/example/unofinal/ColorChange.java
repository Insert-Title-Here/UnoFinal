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

/* The ColorChange screen is the screen that is navigated to during the game
when a Wild or Draw 4 is played. This screen feature a list of all of the colors
and it allows for the game to adjust to the new color that is chosen.
 */
public class ColorChange extends AppCompatActivity {

    //Data class and list to store color values to adapt onto listview
    Data data = new Data();
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_change);

        //Initializing and adding values to list using arrayadapter
        ListView lv = findViewById(R.id.color);

        list = new ArrayList<>();
        list.add("Yellow");
        list.add("Green");
        list.add("Red");
        list.add("Blue");

        ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);

        lv.setAdapter(adapter);


        //Setting listeners for all of the list elements
        listButtonListener(lv, list);


    }

    private void listButtonListener(ListView lv, List<String> list){

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            //Determines which color is selected and based on the position in the list
            //and returns card with the same color
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == 0){
                    data.discard.push(new MainCard(MainCard.Color.YELLOW, null));

                }else if(position == 1){
                    //data.previousCard = new MainCard(MainCard.Color.GREEN, null);
                    data.discard.push(new MainCard(MainCard.Color.GREEN, null));

                }else if(position == 2){
                    data.discard.push(new MainCard(MainCard.Color.RED, null));


                }else {
                    data.discard.push(new MainCard(MainCard.Color.BLUE, null));

                }

                //Completes activity (navigating back to Card class) and allows for other navigation to occur
                finish();
                data.change = true;


            }
        });
    }

}