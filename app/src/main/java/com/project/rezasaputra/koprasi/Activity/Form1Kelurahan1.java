package com.project.rezasaputra.koprasi.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class Form1Kelurahan1 extends AppCompatActivity {


    private static final String TAG = Login.class.getSimpleName();

    private SharedPreferences idkop;
    private EditText inputJumlah;
    Switch simpleSwitch1, simpleSwitch2;
    Button btnNext;
    private ProgressDialog pDialog;
    private SharedPreferences pref_idKelembagaan;

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

        //mengambil data dari switch
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sw1, sw2;
                if (simpleSwitch1.isChecked())
                    sw1 = simpleSwitch1.getTextOn().toString();
                else
                    sw1 = simpleSwitch1.getTextOff().toString();
                if (simpleSwitch2.isChecked())
                    sw2 = simpleSwitch2.getTextOn().toString();
                else
                    sw2 = simpleSwitch2.getTextOff().toString();

                idkop = getSharedPreferences("koperasi", MODE_PRIVATE);
                final String idKop = idkop.getString("id_kop", "");
                String keaktifan  = simpleSwitch1.getTextOn().toString().trim();
                String rapat = simpleSwitch1.getTextOn().toString().trim();
                String jumlah = inputJumlah.getText().toString().trim();

                // ngecek apakah inputannya kosong atau tidak
                if (!idKop.isEmpty() && !keaktifan.isEmpty() && !rapat.isEmpty() && !jumlah.isEmpty()){
                    // login user
                    checkUpload(idKop, keaktifan, rapat, jumlah);
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

    private void checkUpload(final String idKop, final String keaktifan, final String rapat, final String jumlah) {
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
                    String idKelembagaan = jObj.getString("id_klembagaan");
                    //save id kelembagaan
                    SharedPreferences.Editor editor = pref_idKelembagaan.edit();
                    editor.putString("id_kelembagaan", idKelembagaan);
                    editor.commit();

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
                    Intent intent = new Intent(Form1Kelurahan1.this,
                            Form1Kelurahan2.class);
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
