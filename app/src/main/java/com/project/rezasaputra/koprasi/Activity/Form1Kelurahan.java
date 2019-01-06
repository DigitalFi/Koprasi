package com.project.rezasaputra.koprasi.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.project.rezasaputra.koprasi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Form1Kelurahan extends AppCompatActivity {


    private ProgressDialog pDialog;
    private static final String TAG = Form1Kelurahan.class.getSimpleName();
    SharedPreferences pref;
    SharedPreferences id_kop;
    Spinner spinner;
    //AutoCompleteTextView search;
    private Button simpen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form1_kelurahan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinner= (Spinner) findViewById(R.id.namakoprasi);
        simpen=(Button)findViewById(R.id.submit);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        pref = getSharedPreferences("data", Context.MODE_PRIVATE);
        final String kecamatan = pref.getString("id_kec", "");
        final String kelurahan = pref.getString("id_kel", "");

        if (!kecamatan.isEmpty() && !kelurahan.isEmpty()) {
            // login user
            loadSpinnerData(kecamatan, kelurahan);
        } else {
            // jika inputan kosong tampilkan pesan
            Toast.makeText(getApplicationContext(),
                    "Data Tidak Ada", Toast.LENGTH_LONG)
                    .show();
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final koperasi_profile koperasi = (koperasi_profile) adapterView.getSelectedItem();
                SharedPreferences idkop = getApplicationContext().getSharedPreferences("koperasi", 0);// 0 - for private mode
                SharedPreferences.Editor editor = idkop.edit();
                editor.putString("id_kop", koperasi.getId());
                editor.putString("nama", koperasi.getName());
                editor.putString("badanhukum", koperasi.getBadanhukum());
                editor.putString("alamat", koperasi.getAlamat());
                editor.commit();

                //Toast.makeText(Form1Kelurahan.this, "Id Koperasi: "+idkop.getString("id_kop", null)+",  Nama Koperasi : "+koperasi.getName(), Toast.LENGTH_SHORT).show();

                String id = koperasi.getId();
                String nama = koperasi.getName();
                String badanhukum = koperasi.getBadanhukum();
                String alamat = koperasi.getAlamat();
                String tlp = koperasi.getTlp();

                TextView badanhktxt = (TextView) findViewById(R.id.badanhukumKp);
                TextView alamattxt = (TextView) findViewById(R.id.alamatKp);
                TextView tlptxt = (TextView) findViewById(R.id.tlpKp);
                TextView namatxt  = (TextView) findViewById(R.id.namaKp);

                badanhktxt.setText(badanhukum);
                alamattxt.setText(alamat);
                tlptxt.setText(tlp);
                namatxt.setText(nama);

                //pada saat buton klick
                simpen.setOnClickListener(new View.OnClickListener() {

                    String id = koperasi.getId();

                    public void onClick(View view) {
                        // ngecek apakah inputannya kosong atau Tidak
                        if (!id.isEmpty()) {
                            // login user
                            //checkUpload(id);
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

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(Form1Kelurahan.this,MainKelurahan.class);
        startActivity(setIntent);
    }

    private void loadSpinnerData(final String kecamatan, final String kelurahan) {
        // Tag biasanya digunakan ketika ingin membatalkan request volley
        String tag_string_req = "req_spiner";
        pDialog.setMessage("Get Koperasi ...");
        showDialog();

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.URL_KOPRASI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<koperasi_profile> koperasiList = new ArrayList<>();
                hideDialog();
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getInt("success")==1){
                        JSONArray jsonArray=jsonObject.getJSONArray("name");
                        koperasiList.add(new koperasi_profile("", "-Pilih Koperasi-", "-sample-", "-sample", "-sample"));
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            koperasiList.add(new koperasi_profile(jsonObject1.getString("id_koperasi"), jsonObject1.getString("nm_koperasi"), jsonObject1.getString("no_badan_hukum"), jsonObject1.getString("alamat_kantor"),jsonObject1.getString("no_tlp1")));
                        }
                        ArrayAdapter<koperasi_profile> adapter = new ArrayAdapter<koperasi_profile>(Form1Kelurahan.this, android.R.layout.simple_spinner_dropdown_item, koperasiList);
                        spinner.setAdapter(adapter);
                    }
                    ArrayAdapter<koperasi_profile> adapter = new ArrayAdapter<koperasi_profile>(Form1Kelurahan.this, android.R.layout.simple_spinner_dropdown_item, koperasiList);
                    spinner.setAdapter(adapter);
                }catch (JSONException e){e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e(TAG, "Load Error: " + error.getMessage());
                //cek error timeout, noconnection dan network error
                if ( error instanceof TimeoutError || error instanceof NoConnectionError ||error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(),
                            "Please Check Your Connection" + error.getMessage(),
                            Toast.LENGTH_SHORT).show();}
                Intent intent = new Intent(Form1Kelurahan.this,
                        InternetOff.class);
                startActivity(intent);
            }
        }){@Override
        public Map<String, String> getParams(){
            Map<String, String> params = new HashMap<>();
            params.put("id_kec", kecamatan);
            params.put("id_kel", kelurahan);
            return params;
        }
        };
        int socketTimeout = 3000;
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

    /*This function is for contekan*/
    private void setDataKoperasi() {
        ArrayList<koperasi_profile> koperasiList = new ArrayList<>();
        //Add countries

        koperasiList.add(new koperasi_profile("1", "India", "sample", "sample", "sammple"));
        koperasiList.add(new koperasi_profile("2", "USA", "sample", "",""));
        koperasiList.add(new koperasi_profile("3", "China","","",""));
        koperasiList.add(new koperasi_profile("4", "UK","","",""));

        //fill data in spinner
        ArrayAdapter<koperasi_profile> adapter = new ArrayAdapter<koperasi_profile>(Form1Kelurahan.this, android.R.layout.simple_spinner_dropdown_item, koperasiList);
        spinner.setAdapter(adapter);
        //spinner.setSelection(adapter.getPosition(myItem));//Optional to set the selected item.
    }

}
