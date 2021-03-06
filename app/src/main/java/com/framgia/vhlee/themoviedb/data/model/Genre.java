package com.framgia.vhlee.themoviedb.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Genre implements Serializable {
    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    public Genre() {
    }

    public Genre(String id, String name) {
        mId = id;
        mName = name;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
