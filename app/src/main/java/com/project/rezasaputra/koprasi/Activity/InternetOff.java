package com.project.rezasaputra.koprasi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.project.rezasaputra.koprasi.R;

public class InternetOff extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_off);
    }

    public void back(View view) {
        Intent intent = new Intent(InternetOff.this,
                MainKelurahan.class);
        startActivity(intent);

    }
}
