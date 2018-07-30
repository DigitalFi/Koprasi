package com.project.rezasaputra.koprasi.Activity;

/**
 * Created by Muhammad on 6/30/2018.
 */

public class list_approvalkec {

    private String id;
    private String nama;
    private String kelembagaan;
    private String pengawas;
    private String usaha;
    private String perkembangan;

    public list_approvalkec(String id, String nama, String kelembagaan, String pengawas, String usaha, String perkembangan) {
        this.id = id;
        this.nama = nama;
        this.kelembagaan = kelembagaan;
        this.pengawas = pengawas;
        this.usaha = usaha;
        this.perkembangan= perkembangan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKelembagaan() {
        return kelembagaan; }

    public void setKelembagaan(String kelembagaan) {
        this.kelembagaan = kelembagaan;
    }

    public String getPengawas() {
        return pengawas; }

    public void setPengawas(String pengawas) {
        this.pengawas = pengawas;
    }

    public String getUsaha() { return usaha; }

    public void setUsaha(String usaha) {
        this.usaha = usaha;
    }

    public String getPerkembangan() { return perkembangan; }

    public void setPerkembangan(String perkembangan) {
        this.perkembangan = perkembangan;
    }

    //to display object as a string in spinner
    @Override
    public String toString() {
        return nama;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof koperasi_profile){
            koperasi_profile c = (koperasi_profile )obj;
            if(c.getName().equals(nama) && c.getId()==id ) return true;
        }

        return false;
    }

}
