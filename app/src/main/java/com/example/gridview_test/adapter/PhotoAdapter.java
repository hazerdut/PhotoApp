package com.example.gridview_test.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gridview_test.R;
import com.example.gridview_test.RetrofitClient;
import com.example.gridview_test.model.MyPhoto;

import java.util.ArrayList;
import java.util.List;


public class PhotoAdapter extends BaseAdapter {

    private ArrayList<MyPhoto> listPhoto;

    public PhotoAdapter(ArrayList<MyPhoto> listPhoto) {
        this.listPhoto = listPhoto;
    }

    @Override
    public int getCount() {
        return listPhoto.size();
    }

    @Override
    public Object getItem(int i) {
        return listPhoto.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final MyPhoto myPhoto = listPhoto.get(i);

        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_photo, viewGroup, false);
        }

        final ImageView imageView = view.findViewById(R.id.imageView);
        /*final TextView textView = view.findViewById(R.id.textView);*/

        Glide.with(viewGroup.getContext())
                .load(RetrofitClient.URL + myPhoto.getPath())
                .into(imageView);
        /*textView.setText("image " + i);*/
        return view;
    }

    public void updateList(List<MyPhoto> list) {
        listPhoto.clear();
        listPhoto.addAll(list);
        notifyDataSetChanged();
    }
}