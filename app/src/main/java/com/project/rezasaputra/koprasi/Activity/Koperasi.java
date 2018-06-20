package com.project.rezasaputra.koprasi.Activity;

/**
 * Created by Muhammad on 6/5/2018.
 */

public class Koperasi {

    public String idKop;
    public String idKel;
    public String nm_koperasi;
    public String no_badan_hukum;


    public Koperasi() {

    }

    public Koperasi(String idKop, String idKel, String nm_koperasi, String no_badan_hukum) {
        this.idKop = idKop;
        this.idKel = idKel;
        this.nm_koperasi = nm_koperasi;
        this.no_badan_hukum = no_badan_hukum;
    }

    public String getNama() {
        return nm_koperasi;
    }

    public void setNama(String nm_koperasi) {
        this.nm_koperasi = nm_koperasi;
    }

    public String getNoBadan() {
        return no_badan_hukum;
    }

    public void setNoBadan(String no_badan_hukum) {
        this.no_badan_hukum = no_badan_hukum;
    }

    public String getIdKop() { return idKop; }

    public void setIdKop (String idKop) { this.idKop = idKop;}

    public String getIdKel() { return idKel; }

    public void setIdKel (String idKel) { this.idKel = idKel;}



}
