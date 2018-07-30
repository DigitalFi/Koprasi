package com.project.rezasaputra.koprasi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.project.rezasaputra.koprasi.R;

public class SlidePageF1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_page_f1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void lanjut(View view) {
        Intent intent = new Intent(SlidePageF1.this, Form2Kelurahan.class);
        startActivity(intent);
    }
}
