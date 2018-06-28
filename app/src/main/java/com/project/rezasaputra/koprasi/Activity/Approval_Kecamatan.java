package com.project.rezasaputra.koprasi.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.project.rezasaputra.koprasi.R;

public class Approval_Kecamatan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval__kecamatan);

    }

    public void Approval_Kelembagaan(View view) {
        Intent intent = new Intent(Approval_Kecamatan.this, Approval_Kelembagaan.class);
        startActivity(intent);
    }

    public void Approval_Pengurus(View view) {
        Intent intent = new Intent(Approval_Kecamatan.this, Approval_BidangUsaha.class);
        startActivity(intent);
    }

    public void Approval_BidangUsaha(View view) {
        Intent intent = new Intent(Approval_Kecamatan.this, Approval_Pengurus.class);
        startActivity(intent);
    }
}

