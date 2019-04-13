package com.example.hamisha.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.example.hamisha.R;
import com.rey.material.widget.TextView;

public class GridAdapter extends BaseAdapter
{
    private Context ctx;
    private LayoutInflater inflater;

    private String[] thumbnailTitles = {
            "ID", "Driving License", "Insurance", "Passport",
            "NTSA Sticker", "Number plate"
    };

    public GridAdapter(Context ctx) {
        this.ctx = ctx;
        inflater = (LayoutInflater.from(ctx));
    }

    @Override
    public int getCount() {
        return thumbnailTitles.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.document_thumbnail_layout,null);

        TextView label = convertView.findViewById(R.id.thumbnailLabel);

        label.setText(thumbnailTitles[position]);

        return convertView;
    }


}
