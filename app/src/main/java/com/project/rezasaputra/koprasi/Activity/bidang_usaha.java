package com.project.rezasaputra.koprasi.Activity;

/**
 * Created by Muhammad on 6/5/2018.
 */

public class bidang_usaha {
    private String id;
    private String nama;

    public bidang_usaha(String id, String nama) {
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
        if(obj instanceof bidang_usaha){
            bidang_usaha c = (bidang_usaha ) obj;
            if(c.getName().equals(nama) && c.getId()==id ) return true;
        }

        return false;
    }
}
