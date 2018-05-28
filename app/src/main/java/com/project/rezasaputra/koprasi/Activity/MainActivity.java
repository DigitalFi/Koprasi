package com.project.rezasaputra.koprasi.Activity;

import android.content.Intent;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.project.rezasaputra.koprasi.R;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        }, SPLASH_SCREEN);
    }

    public class SwitchCompatActivity extends AppCompatActivity {
        private SwitchCompat switchCompat1;
        private CompoundButton.OnCheckedChangeListener checkedChangeListener;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            setContentView(R.layout.content_form1_kelurahan);
            switchCompat1 = (SwitchCompat) findViewById(R.id.sw_button);
            super.onCreate(savedInstanceState);

            checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Toast.makeText(SwitchCompatActivity.this, compoundButton.getText() + " is " + compoundButton.isChecked(), Toast.LENGTH_SHORT).show();
                }
            };

            switchCompat1.setOnCheckedChangeListener(checkedChangeListener);
        }
    }

    //CAPTURE IMAGE
    public class CameraActivity extends AppCompatActivity {
        static final int ACTION_IMAGE_CAPTURE = 1;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.content_form_kelurahan);
            Button buttonCamera = findViewById(R.id.btnCapturePicture);
            buttonCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent takeCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takeCamera.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takeCamera, ACTION_IMAGE_CAPTURE);
                    }
                }
            });
        }
    }
}


