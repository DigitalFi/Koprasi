package com.project.rezasaputra.koprasi.Activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.project.rezasaputra.koprasi.Activity.helper.AppController;
import com.project.rezasaputra.koprasi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Approval_Kecamatan extends AppCompatActivity {


    private static final String TAG = Login.class.getSimpleName();
    private SharedPreferences idkop;
    Button Approve;
    Button Reject;
    Spinner spinner;
    private ProgressDialog pDialog;
    private SharedPreferences pref;
    private SharedPreferences stsApprove;
    private SharedPreferences idKel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval__kecamatan);

        spinner=(Spinner)findViewById(R.id.namakoprasi);

        Approve = (Button)findViewById(R.id.approve);
        Reject = (Button)findViewById(R.id.reject);

        pref = getSharedPreferences("data", Context.MODE_PRIVATE);
        stsApprove = getSharedPreferences("statusApprove", Context.MODE_PRIVATE);
        idkop = getSharedPreferences("koperasi", Context.MODE_PRIVATE);
        idKel = getSharedPreferences("kelembagaan", Context.MODE_PRIVATE);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        //memanggil data spiner
        loadSpinnerData();

        //mengambil data array dari spiner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final list_approvalkec listkec = (list_approvalkec) adapterView.getSelectedItem();
                SharedPreferences idkel = getApplicationContext().getSharedPreferences("koperasi", 0);// 0 - for private mode
                SharedPreferences.Editor editor = idkel.edit();
                editor.putString("id", listkec.getId());
                editor.commit();

                //fungsi button approve
                Approve.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View view) {

                        //memberikan status
                        SharedPreferences.Editor editor = stsApprove.edit();
                        editor.putString("status_app", "2");
                        editor.commit();

                        final String status = stsApprove.getString("status_app","");
                        final String user = pref.getString("user_id", "");
                        final String idKop = listkec.getId();

                        // ngecek apakah parameter kosong atau Tidak
                        if (!user.isEmpty() && !idKop.isEmpty() && !status.isEmpty()) {
                            // check data parameter
                            checkfunction(user, idKop, status);
                        } else {
                            // jika inputan kosong tampilkan pesan
                            //Toast.makeText(getApplicationContext(),
                            //      "Jangan kosongkan email dan password!", Toast.LENGTH_LONG)
                            //    .show();
                            Toast.makeText(getApplicationContext(), "idKop :" + idKop + "\n" + "user :" + user + "\n" + "status :" + status, Toast.LENGTH_LONG).show(); // display the current state for switch's

                        }
                    }

                });

                //fungsi button reject
                Reject.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View view) {

                        //memberikan status
                        SharedPreferences.Editor editor = stsApprove.edit();
                        editor.putString("status_app", "99");
                        editor.commit();

                        final String status = stsApprove.getString("status_app","");
                        final String user = pref.getString("user_id", "");
                        final String idKop = listkec.getId();

                        // ngecek apakah parameter kosong atau Tidak
                        if (!user.isEmpty() && !idKop.isEmpty() && !status.isEmpty()) {
                            // check data parameter
                            checkfunction(user, idKop, status);
                        } else {
                            // jika inputan kosong tampilkan pesan
//                    Toast.makeText(getApplicationContext(),
//                            "data kosong", Toast.LENGTH_LONG)
                            //Toast.makeText(getApplicationContext(), "idKop :" + idKop + "\n" + "user :" + user + "\n" + "status :" + status, Toast.LENGTH_LONG).show(); // display the current state for switch's

                            //                          .show();
                        }
                    }

                });

                //Toast.makeText(Approval_Kecamatan.this, "Id Koperasi: "+idkel.getString("id", null), Toast.LENGTH_SHORT).show();

                String id = listkec.getId();
                String kelembagaan = listkec.getKelembagaan();
                String pengawas = listkec.getPengawas();
                String usaha = listkec.getUsaha();
                String perkembangan = listkec.getPerkembangan();

                TextView kelembagaantxt = (TextView) findViewById(R.id.statusKl);
                TextView pengawastxt = (TextView) findViewById(R.id.statusPngws);
                TextView usahatxt  = (TextView) findViewById(R.id.statusUsh);
                TextView perkembangantxt  = (TextView) findViewById(R.id.statusPrkmb);

                kelembagaantxt.setText(kelembagaan);
                pengawastxt.setText(pengawas);
                usahatxt.setText(usaha);
                perkembangantxt.setText(perkembangan);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

    }

    private void checkfunction (final String user, final String idKop, final String status){
        // Tag biasanya digunakan ketika ingin membatalkan request volley
        String tag_string_req = "req_login";
        pDialog.setMessage("Loading ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_POST_PRO_KEC, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Post Response: " + response.toString());
                hideDialog();

                try
                {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    // ngecek node error dari api
                    if (!error) {

                        //jika sudah masuk ke mainactivity
                        final String status = stsApprove.getString("status_app","");

                        if (status == "2") {
                            Intent intent = new Intent(Approval_Kecamatan.this,
                                    MainKecamatan.class);
                            startActivity(intent);

                            Toast.makeText(getApplicationContext(), "Data Berhasil Di Approve ", Toast.LENGTH_LONG).show();

                        }else if (status == "99") {
                            Intent intent = new Intent(Approval_Kecamatan.this,
                                    MainKecamatan.class);
                            startActivity(intent);


                            Toast.makeText(getApplicationContext(), "Data Berhasil Di Reject ", Toast.LENGTH_LONG).show();

                        }
                        finish();

                    } else {
                        //terjadi error dan tampilkan pesan error dari API
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
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
                Log.e(TAG, "Login Error: " + error.getMessage());
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
                params.put("status", status);
                params.put("user_id", user);

                return params;
            }
        };
        // menggunakan fungsi volley adrequest yang kita taro di appcontroller
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void loadSpinnerData() {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, AppConfig.URL_GET_LIST_KEC, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<list_approvalkec> koperasiList = new ArrayList<>();
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getInt("success")==1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("name");
                        koperasiList.add(new list_approvalkec("", "-Pilih Koperasi-", "", "", "", ""));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            koperasiList.add(new list_approvalkec(jsonObject1.getString("id_koperasi"), jsonObject1.getString("nm_koperasi"), jsonObject1.getString("status_kelembagaan"), jsonObject1.getString("status_pengawas"), jsonObject1.getString("status_usaha"), jsonObject1.getString("status_perkembangan")));
                        }
                        ArrayAdapter<list_approvalkec> adapter = new ArrayAdapter<list_approvalkec>(Approval_Kecamatan.this, android.R.layout.simple_spinner_dropdown_item, koperasiList);
                        spinner.setAdapter(adapter);
                    }
                       ArrayAdapter<list_approvalkec> adapter = new ArrayAdapter<list_approvalkec>(Approval_Kecamatan.this, android.R.layout.simple_spinner_dropdown_item, koperasiList);
                    spinner.setAdapter(adapter);
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

