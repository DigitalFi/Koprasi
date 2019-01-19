package com.project.rezasaputra.koprasi.Activity;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Switch;
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
import com.project.rezasaputra.koprasi.Activity.helper.AppController;
import com.project.rezasaputra.koprasi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Form1Kelurahan3 extends AppCompatActivity {

    private static final String TAG = Login.class.getSimpleName();

    private SharedPreferences idkop;
    private SharedPreferences pref_idKelembagaan;
    private SharedPreferences idJabatan;
    private SharedPreferences pref;
    private SharedPreferences status;
    private EditText inputNama;
    private EditText inputTlp;
    private EditText inputAlamat;
    private TextView namaKop;
    Switch simpleSwitch1;
    Button btnNext;
    Button btnAdd;
    private ProgressDialog pDialog;

    Spinner spinner;
    String URL="http://koperasidev.lavenderprograms.com/apigw/reff/jabatan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form1_kelurahan3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinner=(Spinner)findViewById(R.id.jabatanpengawas);

        pref_idKelembagaan = getSharedPreferences("data", MODE_PRIVATE);
        idJabatan = getSharedPreferences("dataJabatan", MODE_PRIVATE);
        status = getSharedPreferences("st", MODE_PRIVATE);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        inputNama = (EditText) findViewById(R.id.et_pengurus_nama_kel);
        inputTlp = (EditText) findViewById(R.id.et_pengurus_tlp_kel);
        inputAlamat = (EditText) findViewById(R.id.et_pengurus_alamat_kel);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        namaKop = (TextView) findViewById(R.id.nama_koperasi);

        //ambil data dari pref
        idkop = getSharedPreferences("koperasi", MODE_PRIVATE);
        final String nama = idkop.getString("nama", "");
        //deklarasikan text view
        TextView namakoptxt = (TextView) findViewById(R.id.nama_koperasi);
        //ambil data ke text view
        namakoptxt.setText(nama);

        simpleSwitch1 = (Switch) findViewById(R.id.jenis_klm);

        final String jenisKlm;
        if (simpleSwitch1.isChecked())
            jenisKlm = simpleSwitch1.getTextOn().toString();
        else
            jenisKlm = simpleSwitch1.getTextOff().toString();

        loadSpinnerData(URL);
        pDialog.setMessage("Get Jabatan");
        showDialog();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                jabatan_profile jabatan = (jabatan_profile) adapterView.getSelectedItem();
                final SharedPreferences idJabatan = getApplicationContext().getSharedPreferences("koperasi", 0);// 0 - for private mode
                SharedPreferences.Editor editor = idJabatan.edit();
                editor.putString("id_jabatan", jabatan.getId());
                editor.commit();

                //Toast.makeText(Form1Kelurahan3.this, "Id Jabatan: "+idJabatan.getString("id_jabatan", null)+",  Nama Koperasi : "+jabatan.getName(), Toast.LENGTH_SHORT).show();

                //fungsi buton btnNext
                btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        idkop = getSharedPreferences("koperasi", MODE_PRIVATE);
                        final String idKop = idkop.getString("id_kop", "");
                        pref_idKelembagaan = getSharedPreferences("data", MODE_PRIVATE);
                        final String idKelembagaan = pref_idKelembagaan.getString("id_kelembagaan","");
                        final String id_jabatan = idJabatan.getString("id_jabatan","");
                        String nama = inputNama.getText().toString().trim();
                        String tlp = inputTlp.getText().toString().trim();
                        String alamat = inputAlamat.getText().toString().trim();
                        pref = getSharedPreferences("data", Context.MODE_PRIVATE);
                        final String user = pref.getString("user_name", "");

                        //memberikan status
                        SharedPreferences.Editor editor = status.edit();
                        editor.putInt("status", 2);
                        editor.commit();

                        // ngecek apakah inputannya kosong atau Tidak
                        if (!jenisKlm.isEmpty() && !idKop.isEmpty() && !idKelembagaan.isEmpty() && !id_jabatan.isEmpty() &&  !nama.isEmpty() && !tlp.isEmpty() && !alamat.isEmpty()){
                            // login user
                            checkUpload(jenisKlm, idKop, idKelembagaan, id_jabatan, nama, tlp, alamat, user);
                            //Toast.makeText(getApplicationContext(), "idKop :" + idKop + "\n" + "Switch1 :" + keaktifan + "\n" + "Switch2 :" + rapat + "\n" +  "jumlah :" + jumlah, Toast.LENGTH_LONG).show(); // display the current state for switch's
                        } else {
                            // jika inputan kosong tampilkan pesan
                            Toast.makeText(getApplicationContext(),
                                    "harap isi dengan benar", Toast.LENGTH_LONG)
                                    .show();
                            //Toast.makeText(getApplicationContext(), "idKop :" + idKop + "\n" + "kelembagaan :" + idKelembagaan + "\n" + "jabatan :" + id_jabatan + "\n" +  "nama :" + nama + "\n" +  "tlp :" + tlp + "\n" +  "alamat :" + alamat, Toast.LENGTH_LONG).show(); // display the current state for switch's

                        }

                    }
                });

                //fungsi buton btnNext
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        idkop = getSharedPreferences("koperasi", MODE_PRIVATE);
                        final String idKop = idkop.getString("id_kop", "");
                        pref_idKelembagaan = getSharedPreferences("data", MODE_PRIVATE);
                        final String idKelembagaan = pref_idKelembagaan.getString("id_kelembagaan","");
                        final String id_jabatan = idJabatan.getString("id_jabatan","");
                        String nama = inputNama.getText().toString().trim();
                        String tlp = inputTlp.getText().toString().trim();
                        String alamat = inputAlamat.getText().toString().trim();
                        pref = getSharedPreferences("data", Context.MODE_PRIVATE);
                        final String user = pref.getString("user_id", "");

                        //memberikan status
                        SharedPreferences.Editor editor = status.edit();
                        editor.putInt("status", 1);
                        editor.commit();

                        // ngecek apakah inputannya kosong atau Tidak
                        if (!jenisKlm.isEmpty() && !idKop.isEmpty() && !idKelembagaan.isEmpty() && !id_jabatan.isEmpty() &&  !nama.isEmpty() && !tlp.isEmpty() && !alamat.isEmpty()){
                            // login user
                            checkUpload(jenisKlm, idKop, idKelembagaan, id_jabatan, nama, tlp, alamat, user);
                            //Toast.makeText(getApplicationContext(), "idKop :" + idKop + "\n" + "Switch1 :" + keaktifan + "\n" + "Switch2 :" + rapat + "\n" +  "jumlah :" + jumlah, Toast.LENGTH_LONG).show(); // display the current state for switch's
                        } else {
                            // jika inputan kosong tampilkan pesan
                            Toast.makeText(getApplicationContext(),
                                    "harap isi dengan benar", Toast.LENGTH_LONG)
                                    .show();
                            //Toast.makeText(getApplicationContext(), "idKop :" + idKop + "\n" + "kelembagaan :" + idKelembagaan + "\n" + "jabatan :" + id_jabatan + "\n" +  "nama :" + nama + "\n" +  "tlp :" + tlp + "\n" +  "alamat :" + alamat, Toast.LENGTH_LONG).show(); // display the current state for switch's

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
        Intent setIntent = new Intent(Form1Kelurahan3.this,Form1Kelurahan2.class);
        startActivity(setIntent);
    }


    private void checkUpload(final String jenisKlm, final String idKop, final String idKelembagaan, final String id_jabatan, final String nama, final String tlp, final String alamat, final String user) {
        // Tag biasanya digunakan ketika ingin membatalkan request volley
        String tag_string_req = "req_post_input";
        pDialog.setMessage("Loading ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_INPUT_PENGURUS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Get Response: " + response.toString());
                hideDialog();

                try
                {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    //String idKelembagaan = jObj.getString("id_klembagaan");
                    //save id kelembagaan
                    //SharedPreferences.Editor editor = pref_idKelembagaan.edit();
                    //editor.putString("id_kelembagaan", idKelembagaan);
                    //editor.commit();

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
                        status = getSharedPreferences("st", Context.MODE_PRIVATE);
                        final int stat = status.getInt("status", 0);

                        if (stat == 1) {

                            inputNama.setText("");
                            inputAlamat.setText("");
                            inputTlp.setText("");

                            Toast.makeText(getApplicationContext(),
                                    "Data Sudah Di tambahkan , silahkan isi kembali untuk menambahkan", Toast.LENGTH_LONG)
                                    .show();

                            Intent intent = new Intent(Form1Kelurahan3.this,
                                    Form1Kelurahan3.class);
                            startActivity(intent);

                        }else if(stat == 2){
                            Intent intent = new Intent(Form1Kelurahan3.this,
                                    Form1Kelurahan4.class);
                            startActivity(intent);
                        }
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
                params.put("id_kelembagaan", idKelembagaan);
                params.put("id_jabatan", id_jabatan);
                params.put("nm_pengurus", nama);
                params.put("no_tlp", tlp);
                params.put("alamat", alamat);
                params.put("jen_kel", jenisKlm);
                params.put("user_id", user);

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

    private void loadSpinnerData(String url) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<jabatan_profile> jabatanList = new ArrayList<>();
                try{
                    hideDialog();
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getInt("success")==1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("name");
                        jabatanList.add(new jabatan_profile("", "-Select-"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            jabatanList.add(new jabatan_profile(jsonObject1.getString("id_jabatan"), jsonObject1.getString("nm_jabatan")));
                        }
                        ArrayAdapter<jabatan_profile> adapter = new ArrayAdapter<jabatan_profile>(Form1Kelurahan3.this, android.R.layout.simple_spinner_dropdown_item, jabatanList);
                        spinner.setAdapter(adapter);
                    }
                    ArrayAdapter<jabatan_profile> adapter = new ArrayAdapter<jabatan_profile>(Form1Kelurahan3.this, android.R.layout.simple_spinner_dropdown_item, jabatanList);
                    spinner.setAdapter(adapter);
                }catch (JSONException e){e.printStackTrace();}
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
        });

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    public void next(View view) {
        Intent intent = new Intent(Form1Kelurahan3.this, Form1Kelurahan4.class);
        startActivity(intent);
    }
}
