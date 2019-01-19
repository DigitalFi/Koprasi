package com.project.rezasaputra.koprasi.Activity;

/**
 * Created by Muhammad on 1/6/2019.
 */

public class Bisnis {
    private String idkop;
    private String namakop;
    private String nobadankop;
    private String statuskop;
    private String tglkop;

    public Bisnis(){

    }
    public Bisnis(String idkop, String namakop, String nobadankop,String statuskop,String tglkop){
        this.idkop = idkop;
        this.namakop = namakop;
        this.nobadankop = nobadankop;
        this.statuskop = statuskop;
        this.tglkop = tglkop;
    }

    public String getId_kop() {
        return idkop;
    }

    public void setId_kop(String nm_usaha) {
        this.idkop = idkop;
    }


    public String getNm_kop() {
        return namakop;
    }

    public void setNm_kop(String namakop) {
        this.namakop = namakop;
    }

    public String getNo_Bdn_kop() {
        return nobadankop;
    }

    public void setNo_Bdn_kop(String nobadankop) {
        this.nobadankop = nobadankop;
    }

    public String getStatus_kop() {
        return statuskop;
    }

    public void setStatus_kop(String statuskop) {
        this.statuskop = statuskop;
    }

    public String getTgl_kop() {
        return tglkop;
    }

    public void setTgl_kop(String tglkop) {
        this.tglkop = tglkop;
    }
}

