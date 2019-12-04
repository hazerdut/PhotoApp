package com.example.gridview_test.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyPhoto {
    @SerializedName("_id")
    @Expose
    private String id = "";
    @SerializedName("path")
    @Expose
    private String path = "";
    @SerializedName("__v")
    @Expose
    private int v = 0;

    public MyPhoto(String id, String path, int v) {
        this.id = id;
        this.path = path;
        this.v = v;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }
}
