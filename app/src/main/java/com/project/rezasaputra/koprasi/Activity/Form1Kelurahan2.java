package com.project.rezasaputra.koprasi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.project.rezasaputra.koprasi.R;

public class Form1Kelurahan2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form1_kelurahan2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void next(View view) {
        Intent intent = new Intent(Form1Kelurahan2.this, Form1Kelurahan3.class);
        startActivity(intent);
    }
}
