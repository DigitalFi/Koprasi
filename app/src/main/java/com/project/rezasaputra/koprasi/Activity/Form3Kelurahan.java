package com.project.rezasaputra.koprasi.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.rezasaputra.koprasi.Activity.helper.AppConfig;
import com.project.rezasaputra.koprasi.Activity.helper.AppController;
import com.project.rezasaputra.koprasi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Form3Kelurahan extends AppCompatActivity {

    private static final String TAG = Login.class.getSimpleName();

    private SharedPreferences idUsahaf3;
    private SharedPreferences idStatusf3;
    private SharedPreferences pref_idTemuan;

    private EditText nm_kop, nobdn, tglbdn, almt, nm_pngl, tlp, jml_angt, sbrn_anggota, perizinan ;
    Button btnSubmit;
    private ProgressDialog pDialog;
    private String idTemuan;

    Spinner spinnerst , spinnerkg;
    String reffstatus="https://koperasidev.gobisnis.online/apigw/reff/status_kantor_temuan";
    String reffkgusaha="https://koperasidev.gobisnis.online/apigw/reff/jenis_usaha";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form3_kelurahan);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        pref_idTemuan = getSharedPreferences("idtemuan", MODE_PRIVATE);

        spinnerst=(Spinner)findViewById(R.id.sp_status);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        nm_kop = (EditText) findViewById(R.id.et_nmkop);
        nobdn = (EditText) findViewById(R.id.et_nobdn);
        tglbdn = (EditText) findViewById(R.id.et_tgbdn);
        almt = (EditText) findViewById(R.id.et_alamat);
        nm_pngl = (EditText) findViewById(R.id.et_nmpngl);
        tlp = (EditText) findViewById(R.id.et_tlp);
        jml_angt = (EditText) findViewById(R.id.et_jmlag);
        sbrn_anggota = (EditText) findViewById(R.id.et_sbag);

        loadSpinnerDataStatus(reffstatus);
        spinnerst.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                status_kegiatan status = (status_kegiatan) adapterView.getSelectedItem();
                final SharedPreferences idStatusf3 = getApplicationContext().getSharedPreferences("status", 0);// 0 - for private mode
                SharedPreferences.Editor editor = idStatusf3.edit();
                editor.putString("id_status", status.getId());
                editor.commit();

                //Toast.makeText(Form3Kelurahan.this, "Id Usaha: "+idStatusf3.getString("id_status", null)+",  Nama Koperasi : "+status.getName(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        // ketika login button di klik
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                idUsahaf3 = getSharedPreferences("usaha", MODE_PRIVATE);
                final String id_bidangusaha = idUsahaf3.getString("id_unit_usaha","");
                idStatusf3 = getSharedPreferences("status", MODE_PRIVATE);
                final String id_status = idStatusf3.getString("id_status","");
                String nama = nm_kop.getText().toString().trim();
                String no_badan = nobdn.getText().toString().trim();
                String tgl_badan = tglbdn.getText().toString().trim();
                String alamat = almt.getText().toString().trim();
                String nama_pengelola = nm_pngl.getText().toString().trim();
                String telepon = tlp.getText().toString().trim();
                String jml_anggta  = jml_angt.getText().toString().trim();
                String sebaran  = sbrn_anggota.getText().toString().trim();


                // ngecek apakah inputannya kosong atau Tidak
                if (!nama.isEmpty() &&  !no_badan.isEmpty() && !tgl_badan.isEmpty() && !alamat.isEmpty()&& !id_status.isEmpty()&& !nama_pengelola.isEmpty()&& !telepon.isEmpty()&& !jml_anggta.isEmpty() && !jml_anggta.isEmpty() && !sebaran.isEmpty() ){
                    // login user
                    checkUpload(nama, no_badan, tgl_badan, alamat, id_status, nama_pengelola, telepon, jml_anggta, sebaran);

                    Toast.makeText(getApplicationContext(), nama + "\n" + no_badan + "\n" +  alamat + "\n"  + id_status + "\n"  + nama_pengelola, Toast.LENGTH_LONG).show(); // display the current state for switch's

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

    private void checkUpload(final String nama, final String no_badan, final String tgl_badan, final String alamat, final String id_status, final String nama_pengelola, final String telepon, final String jml_anggta, final String sebaran) {

        // Tag biasanya digunakan ketika ingin membatalkan request volley
        String tag_string_req = "req_login";
        pDialog.setMessage("Loading ...");
        showDialog();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_INPUT_TEMUAN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Get Response: " + response.toString());
                hideDialog();

                try
                {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    String idTemuan = jObj.getString("id_temuan");
                    //save id kelembagaan
                    SharedPreferences.Editor editor = pref_idTemuan.edit();
                    editor.putString("id_temuan", idTemuan);
                    editor.commit();

                    // ngecek node error dari api
                    //if (!error) {

                    //String msg = jObj.getString("msg");

                    final String id = pref_idTemuan.getString("id_temuan", "");
                    //Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG).show();

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
                    Intent intent = new Intent(Form3Kelurahan.this,
                            Form3Kelurahan1.class);
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
                params.put("nm_koperasi", nama);
                params.put("no_badan_hukum", no_badan);
                params.put("tgl_badan_hukum", tgl_badan);
                params.put("status_kantor", id_status);
                params.put("bidang_usaha", tgl_badan);
                params.put("alamat", alamat);
                params.put("nm_pengelola", nama_pengelola);
                params.put("no_tlp", telepon);
                params.put("jml_anggota", jml_anggta);
                params.put("sebaran_anggota", sebaran);

                return params;
            }
        };
        // menggunakan fungsi volley adrequest yang kita taro di appcontroller
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);



    }

    private void loadSpinnerDataStatus(String reffstatus) {

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest=new StringRequest(Request.Method.GET, reffstatus, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<status_kegiatan> statusList = new ArrayList<>();
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getInt("success")==1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("name");
                        statusList.add(new status_kegiatan("", "-Select-"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            statusList.add(new status_kegiatan(jsonObject1.getString("id_status"), jsonObject1.getString("status_koperasi")));
                        }
                        ArrayAdapter<status_kegiatan> adapter = new ArrayAdapter<status_kegiatan>(Form3Kelurahan.this, android.R.layout.simple_spinner_dropdown_item, statusList);
                        spinnerst.setAdapter(adapter);
                    }
                    ArrayAdapter<status_kegiatan> adapter = new ArrayAdapter<status_kegiatan>(Form3Kelurahan.this, android.R.layout.simple_spinner_dropdown_item, statusList);
                    spinnerst.setAdapter(adapter);
                }catch (JSONException e){e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }

    private void loadSpinnerData(String reffstatus) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest=new StringRequest(Request.Method.GET, reffkgusaha, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<bidang_usaha> usahaList = new ArrayList<>();
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getInt("success")==1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("name");
                        usahaList.add(new bidang_usaha("", "-Select-"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            usahaList.add(new bidang_usaha(jsonObject1.getString("id_unit_usaha"), jsonObject1.getString("unit_usaha_koperasi")));
                        }
                        ArrayAdapter<bidang_usaha> adapter = new ArrayAdapter<bidang_usaha>(Form3Kelurahan.this, android.R.layout.simple_spinner_dropdown_item, usahaList);
                        spinnerkg.setAdapter(adapter);
                    }
                    ArrayAdapter<bidang_usaha> adapter = new ArrayAdapter<bidang_usaha>(Form3Kelurahan.this, android.R.layout.simple_spinner_dropdown_item, usahaList);
                    spinnerkg.setAdapter(adapter);
                }catch (JSONException e){e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
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
