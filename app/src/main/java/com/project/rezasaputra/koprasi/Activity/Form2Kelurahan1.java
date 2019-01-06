package com.project.rezasaputra.koprasi.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Form2Kelurahan1 extends AppCompatActivity {

    private static final String TAG = Login.class.getSimpleName();
    private SharedPreferences pref_idPerkembangan;

    private ProgressDialog pDialog;
    private Button btnNext;
    private EditText dataawal_vu, bulanberjalan_vu;
    Switch bulanberjalan_ku;

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
        bulanberjalan_ku = (Switch) findViewById(R.id.bulanberjalan_ku);
        dataawal_vu = (EditText) findViewById(R.id.dataawal_vu);
        bulanberjalan_vu = (EditText) findViewById(R.id.bulanberjalan_vu);

        dataawal_vu.addTextChangedListener(dtvu());
        bulanberjalan_vu.addTextChangedListener(bbvu());

        pref_idPerkembangan = getSharedPreferences("idper", MODE_PRIVATE);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String bulanberjalanku;
                if (bulanberjalan_ku.isChecked())
                    bulanberjalanku = bulanberjalan_ku.getTextOn().toString();
                else
                    bulanberjalanku = bulanberjalan_ku.getTextOff().toString();

                String dtvu = dataawal_vu.getText().toString().trim();
                String bbvu = bulanberjalan_vu.getText().toString().trim();

                final String idPer = pref_idPerkembangan.getString("id_perkembangan", "");

                // ngecek apakah inputannya kosong atau Tidak
                if (!idPer.isEmpty() && !bulanberjalanku.isEmpty() && !dtvu.isEmpty() && !bbvu.isEmpty()){
                    // input data
                    checkUpload(idPer, bulanberjalanku, dtvu, bbvu);
                    //Toast.makeText(getApplicationContext(), "idPer :" + idPer , Toast.LENGTH_LONG).show(); // display the current state for switch's
                } else {
                    // jika inputan kosong tampilkan pesan
                    Toast.makeText(getApplicationContext(),
                            "harap isi dengan benar", Toast.LENGTH_LONG)
                            .show();
                }

            }
        });



    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(Form2Kelurahan1.this,Form2Kelurahan3.class);
        startActivity(setIntent);
    }

    //currency format
    private TextWatcher dtvu() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                dataawal_vu.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    dataawal_vu.setText(formattedString);
                    dataawal_vu.setSelection(dataawal_vu.getText().length());

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                dataawal_vu.addTextChangedListener(this);
            }
        };
    }
    private TextWatcher bbvu() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bulanberjalan_vu.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    bulanberjalan_vu.setText(formattedString);
                    bulanberjalan_vu.setSelection(bulanberjalan_vu.getText().length());

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                bulanberjalan_vu.addTextChangedListener(this);
            }
        };
    }
    //currency format

    private void checkUpload(final String idPer, final String bulanberjalanku, final String dtvu, final String bbvu) {

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
                params.put("id_perkembangan", idPer);
                params.put("kegiatan_usaha_awal", bulanberjalanku);
                params.put("volume_usaha_awal", dtvu);
                params.put("volume_usaha_berjalan", bbvu);
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
