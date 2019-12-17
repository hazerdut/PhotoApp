package com.example.gridview_test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gridview_test.adapter.PhotoAdapter;
import com.example.gridview_test.model.MyPhoto;
import com.example.gridview_test.model.MyPhotoResponse;
import com.example.gridview_test.service.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
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
            intent.putExtra("path",photo.getPath());
            startActivity(intent);
        });

        buttonAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        RetrofitClient.getAPIService().getImages().enqueue(new Callback<MyPhotoResponse>() {
            @Override
            public void onResponse(Call<MyPhotoResponse> call, Response<MyPhotoResponse> response) {
                if (response.body() == null) {
                    Log.d(TAG, "response.body() == null");
                } else {
                    adapterPhoto.updateList(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<MyPhotoResponse> call, Throwable t) {
                Log.e(TAG, "Could not load images. Error: " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_f5: {
                    onStart();
                    return true;
                }
            }
        return false;
    }
}
