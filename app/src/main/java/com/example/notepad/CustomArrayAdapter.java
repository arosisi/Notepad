package com.example.notepad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class CustomArrayAdapter extends ArrayAdapter<Note> {

    private Context context;
    private int resource;

    public CustomArrayAdapter(Context context, int resource, ArrayList<Note> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String content = getItem(position).getContent();
        int imageId = getItem(position).getImageId();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        //something about using "parent" not being best practice below, look into it
        convertView = layoutInflater.inflate(resource, parent, false);

        TextView textView = convertView.findViewById(R.id.textView);
        textView.setText(content);

        ImageView imageView = convertView.findViewById(R.id.imageView);
        imageView.setImageResource(imageId);

        return convertView;
    }
}
