package com.example.appmusic.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CaSi implements Serializable {

    @SerializedName("IdCaSi")
    @Expose
    private String idCaSi;
    @SerializedName("TenCaSi")
    @Expose
    private String tenCaSi;
    @SerializedName("HinhIcon")
    @Expose
    private String hinhIcon;
    @SerializedName("HinhBackground")
    @Expose
    private String hinhBackground;

    public String getIdCaSi() {
        return idCaSi;
    }

    public void setIdCaSi(String idCaSi) {
        this.idCaSi = idCaSi;
    }

    public String getTenCaSi() {
        return tenCaSi;
    }

    public void setTenCaSi(String tenCaSi) {
        this.tenCaSi = tenCaSi;
    }

    public String getHinhIcon() {
        return hinhIcon;
    }

    public void setHinhIcon(String hinhIcon) {
        this.hinhIcon = hinhIcon;
    }

    public String getHinhBackground() {
        return hinhBackground;
    }

    public void setHinhBackground(String hinhBackground) {
        this.hinhBackground = hinhBackground;
    }

}
