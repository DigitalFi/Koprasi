package com.project.rezasaputra.koprasi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.project.rezasaputra.koprasi.R;

public class notifikasi_pemkod extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi_pemkod);

    }

    public void getdtl(View view) {
        Intent intent = new Intent(notifikasi_pemkod.this, Detail_koperasi.class);
        startActivity(intent);
    }
}
