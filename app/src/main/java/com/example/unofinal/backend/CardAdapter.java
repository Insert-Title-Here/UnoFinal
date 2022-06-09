package com.example.unofinal.backend;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.unofinal.R;

import java.util.ArrayList;

public class CardAdapter extends ArrayAdapter<SimpleCard> {

    private Context mContext;
    private int mResource;

    public CardAdapter(@NonNull Context context, int resource, @NonNull ArrayList<SimpleCard> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResource, parent, false);

        ImageView imageView = convertView.findViewById(R.id.image);

        TextView txtName = convertView.findViewById(R.id.list_text);

        imageView.setImageResource(getItem(position).getImage());

        txtName.setText(getItem(position).getName());


        return convertView;
    }
}
