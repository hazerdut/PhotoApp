package com.example.gridview_test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.gridview_test.Utils.UriUtil;
import com.example.gridview_test.model.UploadResponse;
import com.example.gridview_test.service.RetrofitClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CameraActivity extends AppCompatActivity {
    private Uri imageUri;
    private  String status;
    private static final String TAG = ViewPreUploadActivity.class.getName();
    ImageView imageView;
    private static final int REQUEST_CODE_CAMERA = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        setTitle("Take Picture");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageView = findViewById(R.id.preview_camera);
        ActivityCompat.requestPermissions(CameraActivity.this, new String[]{Manifest.permission.CAMERA},REQUEST_CODE_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_CAMERA && grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            startActivityForResult(intent,REQUEST_CODE_CAMERA);
        }else{
            Toast.makeText(this,"Vui lòng cấp quyền cho camera",Toast.LENGTH_SHORT).show();
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CAMERA) {
            data.setAction(Intent.ACTION_GET_CONTENT);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            Bitmap bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");


            Uri uri = getImageUri(CameraActivity.this, bitmap);
            imageUri = uri;
            Glide.with(this).load(imageUri).into(imageView);
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                return true;
            }
            case R.id.uploadImage: {
                if (imageUri != null) {
                    uploadImageToSever();
                    return true;
                }
            }
        }
        return false;
    }

    private void uploadImageToSever() {
        Log.d(TAG, UriUtil.getPathFromUri(this, imageUri));
        File file = new File(UriUtil.getPathFromUri(this, imageUri));
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RetrofitClient.getAPIService().uploadImage(part).enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                Log.d(TAG, "Success to upload image to server.");
                status = "Success to upload image to server.";
            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {
                Log.e(TAG, "Failed to upload image to server. Error: " + t.getLocalizedMessage());
                status = "Failed to upload image to server. Error: ";
            }
        });

        Intent intent = new Intent(CameraActivity.this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "The image is uploading", Toast.LENGTH_LONG).show();

    }
}
