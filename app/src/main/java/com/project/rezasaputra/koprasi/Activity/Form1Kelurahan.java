package com.project.rezasaputra.koprasi.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.project.rezasaputra.koprasi.Activity.helper.AppConfig;
import com.project.rezasaputra.koprasi.Activity.helper.AppController;
import com.project.rezasaputra.koprasi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Form1Kelurahan extends AppCompatActivity {

    private static final String TAG = Login.class.getSimpleName();

    private Button simpen;
    private TextView btnLinkToRegister,skip,reset;
    private EditText inputKeaktifan;
    private Button btnCapture;
    private ImageView ImageView;
    private EditText inputJumlah;
    private EditText inputRapat;
    private ProgressDialog pDialog;
    private SharedPreferences idkop;
    private SharedPreferences form1;
    private SharedPreferences pref;
    String mCurrentPhotoPath;

    static final int REQUEST_TAKE_PHOTO = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form1_kelurahan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        form1 = getSharedPreferences("dataForm1", MODE_PRIVATE);
        idkop = getSharedPreferences("koperasi", MODE_PRIVATE);
        pref = getSharedPreferences("data", MODE_PRIVATE);


        inputKeaktifan = (EditText) findViewById(R.id.et_tlp_kel);
        inputRapat = (EditText) findViewById(R.id.et_website_kel);
        inputJumlah = (EditText) findViewById(R.id.et_email_kel);
        btnCapture = (Button) findViewById(R.id.btnCapture);
        ImageView = (ImageView) findViewById(R.id.ImageView);
        simpen = (Button) findViewById(R.id.simpen);
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        final String idKop = idkop.getString("id_kop", "");

        // ketika submitbutton di click
        simpen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String keaktifan  = inputKeaktifan.getText().toString().trim();
                String rapat = inputRapat.getText().toString().trim();
                String jumlah = inputJumlah.getText().toString().trim();
                final String nama = pref.getString("name", "");
                final String idKop = idkop.getString("id_kop", "");


                // ngecek apakah inputannya kosong atau tidak
                if (!idKop.isEmpty() && !keaktifan.isEmpty() && !rapat.isEmpty() && !jumlah.isEmpty() && !nama.isEmpty()) {
                    // login user
                    //checkUpload(idKop, keaktifan, rapat, jumlah, nama);
                    Intent intent = new Intent(Form1Kelurahan.this,
                            Form1Kelurahan1.class);
                    startActivity(intent);
                    finish();
                } else {
                    // jika inputan kosong tampilkan pesan
                    Toast.makeText(getApplicationContext(),
                            "harap isi dengan benar", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView.setImageBitmap(imageBitmap);
        }
    }

    private void checkUpload(String kop, final String keaktifan, final String rapat, final String jumlah, final String idKop) {
        // Tag biasanya digunakan ketika ingin membatalkan request volley
        String tag_string_req = "req_login";
        pDialog.setMessage("Loading ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_INPUT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Get Response: " + response.toString());
                hideDialog();

                try
                {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray jObj1 = jObj.getJSONArray("data");
                    String idkop = jObj1.getString(Integer.parseInt("id_koperasi"));

                    // ngecek node error dari api
                    //if (jObj.getString("status")==success) {

                        //String msg = jObj.getString("msg");
                        Toast.makeText(getApplicationContext(), idkop, Toast.LENGTH_LONG).show();

                       /* JSONObject jObj1 = jObj.getString("data");
                        String idkop = jObj1.getString("id_koperasi");
                        String tanggal = jObj1.getString("tgl_kunjungan");
                        String status = jObj1.getString("status_keaktifan");
                        String rapat = jObj1.getString("rapat_anggota");
                        String jumlah = jObj1.getString("jml_anggota");
                        String dateinput = jObj1.getString("create_date");
                        String namainput = jObj1.getString("create_by");

                        SharedPreferences.Editor editor = form1.edit();
                        editor.putString("idSes", idkop);
                        editor.putString("tglSes", tanggal);
                        editor.putString("keaktifanSes", status);
                        editor.putString("rapatSes", rapat);
                        editor.putString("jumlahSes", jumlah);
                        editor.putString("createSes", dateinput);
                        editor.putString("namaSes", namainput);
                        editor.commit();*/

                        //jika sudah masuk ke mainactivity
                            Intent intent = new Intent(Form1Kelurahan.this,
                                    Form1Kelurahan1.class);
                            startActivity(intent);
                            finish();
                    //} else {
                        //terjadi error dan tampilkan pesan error dari API
                     //   Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
                   // }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Data Error: " + error.getMessage());
                //cek error timeout, noconnection dan network error
                if ( error instanceof TimeoutError || error instanceof NoConnectionError ||error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(),
                            "Please Check Your Connection",
                            Toast.LENGTH_SHORT).show();}
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // kirim parameter ke server
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_koperasi", idKop);
                params.put("status_keaktifan", keaktifan);
                //params.put("create_by", nama);
                params.put("jml_anggota", jumlah);
                params.put("rapat_anggota", rapat);

                return params;
            }
        };
        // menggunakan fungsi volley adrequest yang kita taro di appcontroller
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    //untuk menampilkan loading dialog
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    public void next(View view) {
        Intent intent = new Intent(Form1Kelurahan.this, Form1Kelurahan1.class);
        startActivity(intent);
    }
}
