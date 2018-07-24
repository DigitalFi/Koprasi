package com.project.rezasaputra.koprasi.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.project.rezasaputra.koprasi.Activity.helper.AppConfig;
import com.project.rezasaputra.koprasi.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Form1Kelurahan1 extends AppCompatActivity {


    private static final String TAG = Login.class.getSimpleName();

    private SharedPreferences idkop;
    private EditText inputJumlah;
    private Button btnCapture;
    private ImageView imageView;
    Switch simpleSwitch1, simpleSwitch2;
    Button btnNext;
    private ProgressDialog pDialog;
    private SharedPreferences pref_idKelembagaan;

    static final int REQUEST_TAKE_PHOTO = 1;

    String jumlah;


    String sw_keaktifan, sw_rapat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form1_kelurahan1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pref_idKelembagaan = getSharedPreferences("data", MODE_PRIVATE);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        inputJumlah = (EditText) findViewById(R.id.et_jml_anggota_kel);

        simpleSwitch1 = (Switch) findViewById(R.id.simpleSwitch1);
        simpleSwitch2 = (Switch) findViewById(R.id.simpleSwitch2);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnCapture = (Button) findViewById(R.id.btnCapture);
        imageView = (ImageView) findViewById(R.id.ImageView);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            final Bitmap bitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(bitmap);

            //mengambil data dari switch
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (simpleSwitch1.isChecked())
                        sw_keaktifan = simpleSwitch1.getTextOn().toString();
                    else
                        sw_keaktifan = simpleSwitch1.getTextOff().toString();
                    if (simpleSwitch2.isChecked())
                        sw_rapat = simpleSwitch2.getTextOn().toString();
                    else
                        sw_rapat = simpleSwitch2.getTextOff().toString();

                    idkop = getSharedPreferences("koperasi", MODE_PRIVATE);
                    final String idKop = idkop.getString("id_kop", "");

                    //jika sudah masuk ke mainactivity
                    if(sw_keaktifan.length() == 5){

                        uploadBitmap(bitmap);

                        //Toast.makeText(getApplicationContext(), "idKop :" + idKop + "\n" + "Switch1 :" + sw_keaktifan + "\n" + "Switch2 :" + sw_rapat + "\n" +  "jumlah :" + jumlah, Toast.LENGTH_LONG).show(); // display the current state for switch's

                        Intent intent = new Intent(Form1Kelurahan1.this,
                                Form1Kelurahan2.class);
                        startActivity(intent);
                        finish();
                    } else {

                        uploadBitmap(bitmap);

                        Intent intent = new Intent(Form1Kelurahan1.this,
                                SlidePageTdkaktif.class);
                        startActivity(intent);
                        finish();

                        Toast.makeText(getApplicationContext(), "idKop :" + idKop + "\n" + "Switch1 :" + sw_keaktifan + "\n" + "Switch2 :" + sw_rapat + "\n" +  "jumlah :" + jumlah, Toast.LENGTH_LONG).show(); // display the current state for switch's

                        //Toast.makeText(getApplicationContext(), "Koperasi Tidak Aktif , Selesai", Toast.LENGTH_LONG).show();

                    }
                        // ngecek apakah inputannya kosong atau Tidak
                            // login user


                }
            });

        }
    }


    /*
    * The method is taking Bitmap as an argument
    * then it will return the byte[] array for the given bitmap
    * and we will send this array to the server
    * here we are using PNG Compression with 80% quality
    * you can give quality between 0 to 100
    * 0 means worse quality
    * 100 means best quality
    * */
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadBitmap(final Bitmap bitmap) {


        jumlah = inputJumlah.getText().toString().trim();

        idkop = getSharedPreferences("koperasi", MODE_PRIVATE);
        final String idKop = idkop.getString("id_kop", "");

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, AppConfig.URL_INPUT_KELEMBAGAAN,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                            String idKelembagaan = obj.getString("id_kelembagaan");
                            //save id kelembagaan
                            SharedPreferences.Editor editor = pref_idKelembagaan.edit();
                            editor.putString("id_kelembagaan", idKelembagaan);
                            editor.commit();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
            * If you want to add more parameters with the image
            * you can do it here
            * here we have only one parameter with the image
            * which is tags
            * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_koperasi", idKop);
                params.put("status_keaktifan", sw_keaktifan);
                params.put("rapat_anggota", sw_rapat);
                params.put("jml_anggota", jumlah);
                return params;
            }

            /*
            * Here we are passing image by renaming it with a unique name
            * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".jpg", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    /*private void checkUpload(final String idKop, final String keaktifan, final String rapat, final String jumlah) {
        // Tag biasanya digunakan ketika ingin membatalkan request volley
        String tag_string_req = "req_login";
        pDialog.setMessage("Loading ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_INPUT_KELEMBAGAAN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Get Response: " + response.toString());
                hideDialog();

                try
                {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    JSONArray stObj = jObj.getJSONArray("data");
                    String idKelembagaan = jObj.getString("id_klembagaan");
                    //save id kelembagaan
                    SharedPreferences.Editor editor = pref_idKelembagaan.edit();
                    editor.putString("id_kelembagaan", idKelembagaan);
                    editor.commit();

                    // ngecek node error dari api
                    if (!error) {
                        // user berhasil login
                        JSONObject data = stObj.getJSONObject(0);
                        String str_status = data.getString("status_keaktifan").trim();

                       // Toast.makeText(getApplicationContext(), "status :"+str_status, Toast.LENGTH_LONG).show();

                        //jika sudah masuk ke mainactivity
                        if(str_status.length() == 5){
                            Intent intent = new Intent(Form1Kelurahan1.this,
                                    Form1Kelurahan2.class);
                            startActivity(intent);
                            finish();

                            //Toast.makeText(getApplicationContext(), "atas :"+str_status, Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(Form1Kelurahan1.this,
                                    SlidePageTdkaktif.class);
                            startActivity(intent);
                            finish();

                            Toast.makeText(getApplicationContext(), "Koperasi Tidak Aktif , Selesai", Toast.LENGTH_LONG).show();
                        }
                    } else {
                    //terjadi error dan tampilkan pesan error dari API
                       Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
                    }
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
                params.put("rapat_anggota", rapat);
                params.put("jml_anggota", jumlah);

                return params;
            }
        };
        // menggunakan fungsi volley adrequest yang kita taro di appcontroller
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }*/

    //untuk menampilkan loading dialog
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
