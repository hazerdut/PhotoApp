package com.example.gridview_test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gridview_test.adapter.PhotoAdapter;
import com.example.gridview_test.model.MyPhoto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    PhotoAdapter adapterPhoto = new PhotoAdapter(new ArrayList<MyPhoto>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("My Media");
        GridView gridView = findViewById(R.id.gridview_main);
        gridView.setAdapter(adapterPhoto);
        ImageButton buttonAdd = findViewById(R.id.btnAdd);
        ImageButton buttonCamera = findViewById(R.id.btn_camera);

        buttonCamera.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CameraActivity.class);
            startActivity(intent);
        });

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            // start detail activity
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            MyPhoto photo = (MyPhoto) adapterPhoto.getItem(position);
            intent.putExtra("image", RetrofitClient.URL + photo.getPath());
            startActivity(intent);
        });

        buttonAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });

        RetrofitClient.getApi().getContacts().enqueue(new Callback<List<MyPhoto>>() {
            @Override
            public void onResponse(Call<List<MyPhoto>> call, Response<List<MyPhoto>> response) {
                if (response.body() == null) {
                    Log.d("@@@@@", "error null data");
                } else {
                    adapterPhoto.updateList(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<MyPhoto>> call, Throwable t) {
                Log.e("error__", "loi load anh");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        RetrofitClient.getApi().getContacts().enqueue(new Callback<List<MyPhoto>>() {
            @Override
            public void onResponse(Call<List<MyPhoto>> call, Response<List<MyPhoto>> response) {
                if (response.body() == null) {
                    Log.d("@@@@@", "error null data");
                } else {
                    adapterPhoto.updateList(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<MyPhoto>> call, Throwable t) {
                Log.e("error__", "loi load anh");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
}
