package com.example.appmusic.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TopSong implements Serializable {

    @SerializedName("IdTopSong")
    @Expose
    private String idTopSong;
    @SerializedName("TenTopSong")
    @Expose
    private String tenTopSong;
    @SerializedName("HinhTopSong")
    @Expose
    private String hinhTopSong;

    public String getIdTopSong() {
        return idTopSong;
    }

    public void setIdTopSong(String idTopSong) {
        this.idTopSong = idTopSong;
    }

    public String getTenTopSong() {
        return tenTopSong;
    }

    public void setTenTopSong(String tenTopSong) {
        this.tenTopSong = tenTopSong;
    }

    public String getHinhTopSong() {
        return hinhTopSong;
    }

    public void setHinhTopSong(String hinhTopSong) {
        this.hinhTopSong = hinhTopSong;
    }

}
