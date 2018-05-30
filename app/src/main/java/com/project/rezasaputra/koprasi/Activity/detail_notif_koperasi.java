package com.project.rezasaputra.koprasi.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.project.rezasaputra.koprasi.R;

public class detail_notif_koperasi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_notif_koperasi);
    }

    public void approve(View view) {
        Toast.makeText(getApplicationContext(), "Data Berhasil Disimpan", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(detail_notif_koperasi.this, MainKecamatan.class);
        startActivity(intent);
    }

    public void decline(View view) {
        Toast.makeText(getApplicationContext(), "Data Di Tolak", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(detail_notif_koperasi.this, MainKecamatan.class);
        startActivity(intent);
    }
}
