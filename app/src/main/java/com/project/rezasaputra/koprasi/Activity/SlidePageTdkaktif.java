package com.project.rezasaputra.koprasi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.project.rezasaputra.koprasi.R;

public class SlidePageTdkaktif extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_page_tdkaktif);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void lanjut(View view) {
        Intent intent = new Intent(SlidePageTdkaktif.this,
                MainKelurahan.class);
        startActivity(intent);
    }
}
