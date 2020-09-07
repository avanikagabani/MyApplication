package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


public class ListviewAdapter extends ArrayAdapter<Note> {


    public ListviewAdapter(@NonNull Context context, int resource, @NonNull List<Note> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (position==0){
                return LayoutInflater.from(this.getContext()).inflate(R.layout.new_note,parent,false);
        }
        convertView= LayoutInflater.from(this.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false);
        Note note=getItem(position);
        TextView textView=(TextView)convertView.findViewById(android.R.id.text1);
        textView.setText(note.getTitle());
        return convertView;
    }
}
