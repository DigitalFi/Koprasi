package com.project.rezasaputra.koprasi.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Form2Kelurahan1 extends AppCompatActivity {

    private static final String TAG = Login.class.getSimpleName();

    private ProgressDialog pDialog;
    private Button btnNext;
    private EditText dataawal_ku, bulanberjalan_ku;
    Switch dataawal_vu, bulanberjalan_vu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form2_kelurahan1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // inisialisasi variable
        btnNext = (Button) findViewById(R.id.btnNext);
        dataawal_vu = (Switch) findViewById(R.id.dataawal_vu);
        bulanberjalan_vu = (Switch) findViewById(R.id.bulanberjalan_vu);
        dataawal_ku = (EditText) findViewById(R.id.dataawal_ku);
        bulanberjalan_ku = (EditText) findViewById(R.id.bulanberjalan_ku);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dataawalvu;
                if (dataawal_vu.isChecked())
                    dataawalvu = dataawal_vu.getTextOn().toString();
                else
                    dataawalvu = dataawal_vu.getTextOff().toString();

                //mengambil data dari switch
                String dtvu = dataawal_vu.getTextOn().toString().trim();
                String bbvu = bulanberjalan_vu.getTextOn().toString().trim();
                String dtku = dataawal_ku.getText().toString().trim();
                String bbku = bulanberjalan_ku.getText().toString().trim();

                // ngecek apakah inputannya kosong atau tidak
                if (!dtvu.isEmpty() && !bbvu.isEmpty() && !dtku.isEmpty() && !bbku.isEmpty()){
                    // input data
                    checkUpload(dtvu, bbvu, dtku, bbku);
                    //Toast.makeText(getApplicationContext(), "idKop :" + idKop + "\n" + "Switch1 :" + keaktifan + "\n" + "Switch2 :" + rapat + "\n" +  "jumlah :" + jumlah, Toast.LENGTH_LONG).show(); // display the current state for switch's
                } else {
                    // jika inputan kosong tampilkan pesan
                    Toast.makeText(getApplicationContext(),
                            "harap isi dengan benar", Toast.LENGTH_LONG)
                            .show();
                }

            }
        });

    }

    private void checkUpload(final String dtvu, final String bbvu, final String dtku, final String bbku) {

        // Tag biasanya digunakan ketika ingin membatalkan request volley
        String tag_string_req = "req_login";
        pDialog.setMessage("Loading ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_INPUT_PERKEMBANGAN_USAHA, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Get Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    //save id kelembagaan

                    // ngecek node error dari api
                    if (!error) {
                        // user berhasil login
                        /*JSONObject data = jObj.getJSONObject("data");
                        String koperasi_id = data.getString("id_koperasi");
                        String keaktifan_status = data.getString("status_keaktifan");
                        String anggota_rapat = data.getString("rapat_anggota");
                        String roles_name = data.getString("jml_anggota");
                        String kec_id = data.getString("photo");
                        String kel_id = jObj.getString("create_date");
                        Integer roles_id = jObj.getInt("create_by");*/

                        //jika sudah masuk ke mainactivity
                        Intent intent = new Intent(Form2Kelurahan1.this,
                                Form2Kelurahan2.class);
                        startActivity(intent);
                        finish();
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
                if (error instanceof TimeoutError || error instanceof NoConnectionError || error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(),
                            "Please Check Your Connection",
                            Toast.LENGTH_SHORT).show();
                }
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // kirim parameter ke server
                Map<String, String> params = new HashMap<String, String>();
                params.put("kegiatan_usaha_awal", dtvu);
                params.put("kegiatan_usaha_berjalan", bbvu);
                params.put("volume_usaha_awal", dtku);
                params.put("volume_usaha_berjalan", bbku);
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


}
