package com.project.rezasaputra.koprasi.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.project.rezasaputra.koprasi.R;

import java.util.List;

public class notifikasi_pemkod extends AppCompatActivity {

   private ListView listnya;
   private String[] isilistnya = new String[] {
           "Notifikasi Belum Di Approve", "Sudah Di Approve",
           "Sudah Di Approve", "Notifikasi Belum Di Approve"
   };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi_pemkod);

        listnya = (ListView) findViewById(R.id.listview);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(notifikasi_pemkod.this,android.R.layout.simple_list_item_1,
                android.R.id.text1, (List<String>) listnya);
        listnya.setAdapter(adapter);

    }
}
