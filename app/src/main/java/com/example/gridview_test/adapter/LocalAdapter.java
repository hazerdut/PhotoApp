package com.example.gridview_test.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gridview_test.R;
import com.example.gridview_test.model.MyPhoto;

import java.util.ArrayList;

public class LocalAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<String> listPhoto;

    public LocalAdapter(Context context, int layout, ArrayList<String> listPhoto) {
        this.context = context;
        this.layout = layout;
        this.listPhoto = listPhoto;
    }

    @Override
    public int getCount() {
        return listPhoto.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public View getView(int position, View view, ViewGroup viewGroup){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.row_photo,null);
        /*TextView textView = (TextView)view.findViewById(R.id.textView);*/
        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
        String photo = listPhoto.get(position);
        /*textView.setText(photo.getName());*/
        Glide.with(context)
                .load(photo)
                .centerCrop()
                .into(imageView);
        return view;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}