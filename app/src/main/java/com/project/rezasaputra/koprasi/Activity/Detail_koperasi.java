package com.project.rezasaputra.koprasi.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.project.rezasaputra.koprasi.R;

public class Detail_koperasi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_koperasi);
    }

    public void approve(View view) {
        Toast.makeText(getApplicationContext(), "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Detail_koperasi.this, MainPemkod.class);
        startActivity(intent);
    }

    public void decline(View view) {
        Toast.makeText(getApplicationContext(), "Data Ditolak", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Detail_koperasi.this, MainPemkod.class);
        startActivity(intent);
    }
}
