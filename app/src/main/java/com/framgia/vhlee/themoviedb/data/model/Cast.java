package com.framgia.vhlee.themoviedb.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Cast implements Serializable {
    @SerializedName("cast_id")
    private int mCastId;

    @SerializedName("character")
    private String mCharacter;

    @SerializedName("name")
    private String mName;

    @SerializedName("profile_path")
    private String mProfilePath;

    public Cast() {
    }

    public int getCastId() {
        return mCastId;
    }

    public void setCastId(int castId) {
        mCastId = castId;
    }

    public String getCharacter() {
        return mCharacter;
    }

    public void setCharacter(String character) {
        mCharacter = character;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getProfilePath() {
        return mProfilePath;
    }

    public void setProfilePath(String profilePath) {
        mProfilePath = profilePath;
    }
}
