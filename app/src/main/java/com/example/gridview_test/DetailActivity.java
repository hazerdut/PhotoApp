package com.example.gridview_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gridview_test.model.DeleteResponse;
import com.example.gridview_test.model.UploadResponse;
import com.example.gridview_test.service.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = AddActivity.class.getName();
    ImageView imageView;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
        path = intent.getStringExtra("path");

        setTitle("My Media");
        imageView = findViewById(R.id.imageViewDetail);
        Glide.with(this)
                .load(image)
                .into(imageView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                return true;
            }
            case R.id.delete: {
                deleteImage(path);
                return true;
                }
            }

        return false;
    }


    private void deleteImage(String path) {

        RetrofitClient.getAPIService().deleteImage(path).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "Success to upload image to server.");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "Success to upload image to server.");
            }
        });

        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "The image was deleted", Toast.LENGTH_LONG).show();
    }
}


