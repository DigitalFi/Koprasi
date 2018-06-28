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

public class Form2Kelurahan2 extends AppCompatActivity {



    private static final String TAG = Login.class.getSimpleName();

    private ProgressDialog pDialog;
    private Button btnNext;
    private EditText dt_sp, bb_sp, dt_sw, bb_sw, dt_ss, bb_ss, dt_pu, bb_pu, dt_hu, bb_hu, dt_cd, bb_cd, dt_shu, bb_shu, et_masalah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form2_kelurahan2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // inisialisasi variable
        btnNext = (Button) findViewById(R.id.btnNext);
        dt_sp = (EditText) findViewById(R.id.dt_sp);
        bb_sp = (EditText) findViewById(R.id.bb_sp);
        dt_sw = (EditText) findViewById(R.id.dt_sw);
        bb_sw = (EditText) findViewById(R.id.bb_sw);
        dt_ss = (EditText) findViewById(R.id.dt_ss);
        bb_ss = (EditText) findViewById(R.id.bb_ss);
        dt_pu = (EditText) findViewById(R.id.dt_pu);
        bb_pu = (EditText) findViewById(R.id.bb_pu);
        dt_hu = (EditText) findViewById(R.id.dt_hu);
        bb_hu = (EditText) findViewById(R.id.bb_hu);
        dt_cd = (EditText) findViewById(R.id.dt_cd);
        bb_cd = (EditText) findViewById(R.id.bb_cd);
        dt_shu = (EditText) findViewById(R.id.dt_shu);
        bb_shu = (EditText) findViewById(R.id.bb_shu);
        et_masalah = (EditText) findViewById(R.id.et_masalah);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //mengambil data dari switch
                String dtsp = dt_sp.getText().toString().trim();
                String bbsp = bb_sp.getText().toString().trim();
                String dtsw = dt_sw.getText().toString().trim();
                String bbsw = bb_sw.getText().toString().trim();
                String dtss = dt_ss.getText().toString().trim();
                String bbss = bb_ss.getText().toString().trim();
                String dtpu = dt_pu.getText().toString().trim();
                String bbpu = bb_pu.getText().toString().trim();
                String dthu = dt_hu.getText().toString().trim();
                String bbhu = bb_hu.getText().toString().trim();
                String dtcd = dt_cd.getText().toString().trim();
                String bbcd = bb_cd.getText().toString().trim();
                String dtshu = dt_shu.getText().toString().trim();
                String bbshu = bb_shu.getText().toString().trim();
                String etmasalah = et_masalah.getText().toString().trim();

                //mengecek apakah inputan kosong
                if (!dtsp.isEmpty() && !bbsp.isEmpty() && !dtsw.isEmpty() && !bbsw.isEmpty() && !dtss.isEmpty() && !bbss.isEmpty() && !dtpu.isEmpty() && !bbpu.isEmpty() && !dthu.isEmpty() && !bbhu.isEmpty() && !dtcd.isEmpty() && !bbcd.isEmpty() && !dtshu.isEmpty() && !bbshu.isEmpty() && !etmasalah.isEmpty()){
                    // input data
                    checkUpload(dtsp, bbsp, dtsw, bbsw, dtss, bbss, dtpu, bbpu, dthu, bbhu, dtcd, bbcd, dtshu, bbshu, etmasalah);
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

    private void checkUpload(final String dtsp, final String bbsp, final String dtsw, final String bbsw, final String dtss, final String bbss, final String dtpu, final String bbpu, final String dthu, final String bbhu, final String dtcd, final String bbcd, final String dtshu, final String bbshu, final String etmasalah) {

        // Tag biasanya digunakan ketika ingin membatalkan request volley
        String tag_string_req = "req_login";
        pDialog.setMessage("Loading ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_INPUT_PERKEMBANGAN_KEUANGAN, new Response.Listener<String>() {

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
                        Intent intent = new Intent(Form2Kelurahan2.this,
                                Form3Kelurahan.class);
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
                params.put("jml_simpok_awal", dtsp);
                params.put("jml_simpok_berjalan", bbsp);
                params.put("jml_simwaj_awal", dtsw);
                params.put("jml_simwaj_berjalan", bbsw);
                params.put("jml_sim_srela_awal", dtss);
                params.put("jml_sim_srela_berjalan", bbss);
                params.put("piutang_awal", dtpu);
                params.put("piutang_berjalan", bbpu);
                params.put("hutang_awal", dthu);
                params.put("hutang_berjalan", bbhu);
                params.put("cadangan_awal", dtcd);
                params.put("cadangan_berjalan", bbcd);
                params.put("shu_awal", dtshu);
                params.put("shu_berjalan", bbshu);
                params.put("permasalahan", etmasalah);

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
