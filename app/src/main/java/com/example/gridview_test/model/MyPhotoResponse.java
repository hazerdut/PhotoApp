package com.example.gridview_test.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyPhotoResponse {
    @SerializedName("data")
    private List<MyPhoto> data;
    @SerializedName("error")
    private String error;

    public List<MyPhoto> getData() {
        return data;
    }

    public void setData(List<MyPhoto> data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
