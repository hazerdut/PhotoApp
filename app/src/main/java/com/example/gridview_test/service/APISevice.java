package com.example.gridview_test.service;

import com.example.gridview_test.model.DeleteResponse;
import com.example.gridview_test.model.MyPhotoResponse;
import com.example.gridview_test.model.UploadResponse;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APISevice {
    @GET("api/images")
    Call<MyPhotoResponse> getImages();

    @Multipart
    @POST("api/upload")
    Call<UploadResponse> uploadImage(@Part MultipartBody.Part part);

    @DELETE("api/images/{filepath}")
    Call<ResponseBody> deleteImage(@Path("filepath") String path);
}