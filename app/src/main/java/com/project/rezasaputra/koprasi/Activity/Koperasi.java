package com.project.rezasaputra.koprasi.Activity;

/**
 * Created by Muhammad on 6/5/2018.
 */

public class Koperasi {

    public String nm_koperasi;
    public String no_badan_hukum;


    public Koperasi() {

    }

    public Koperasi(String nm_koperasi, String no_badan_hukum) {
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

}
