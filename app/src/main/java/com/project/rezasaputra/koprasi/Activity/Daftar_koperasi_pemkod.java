package com.project.rezasaputra.koprasi.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.project.rezasaputra.koprasi.R;

import java.util.List;

public class Daftar_koperasi_pemkod extends AppCompatActivity {
    private ListView listnya;
    private String[] isilistnya = new String[] {
            "Koperasi Pemkod", "Koperasi Suka Cita",
            "Koperasi adi daya", "Koperasi Setia Hati"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_koperasi_pemkod);

        listnya = (ListView) findViewById(R.id.listview);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(Daftar_koperasi_pemkod.this,android.R.layout.simple_list_item_1,
                android.R.id.text1, (List<String>) listnya);
        listnya.setAdapter(adapter);
    }
}
