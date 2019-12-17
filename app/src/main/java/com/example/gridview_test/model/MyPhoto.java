package com.example.gridview_test.model;

import com.google.gson.annotations.SerializedName;

public class MyPhoto {
    @SerializedName("filepath")
    private String path;

    public MyPhoto(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
