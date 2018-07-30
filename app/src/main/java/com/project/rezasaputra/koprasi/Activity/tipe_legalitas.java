package com.project.rezasaputra.koprasi.Activity;

/**
 * Created by Muhammad on 7/15/2018.
 */

public class tipe_legalitas {
    private String id;
    private String nama;

    public tipe_legalitas(String id, String nama) {
        this.id = id;
        this.nama = nama;
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

    //to display object as a string in spinner
    @Override
    public String toString() {
        return nama;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof tipe_legalitas){
            tipe_legalitas c = (tipe_legalitas ) obj;
            if(c.getName().equals(nama) && c.getId()==id ) return true;
        }

        return false;
    }
}
