package com.project.rezasaputra.koprasi.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class History_Kelurahan extends AppCompatActivity {

    private ProgressDialog pDialog;

    private static final String TAG = Login.class.getSimpleName();

    private RecyclerView mList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Koperasi> kopList;
    private RecyclerView.Adapter adapter;
    private SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history__kelurahan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        mList = findViewById(R.id.main_list);

        kopList = new ArrayList<>();
        adapter = new KoperasiAdapter(getApplicationContext(),kopList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);

        //mengambil data dari pref
        pref = getSharedPreferences("data", Context.MODE_PRIVATE);
        final String idUser = pref.getString("user_id", "");

        // ngecek apakah inputannya kosong atau Tidak
        if (!idUser.isEmpty()) {
            // login user
            getData(idUser);
        } else {
            // jika inputan kosong tampilkan pesan
            Toast.makeText(getApplicationContext(),
                    "Error", Toast.LENGTH_LONG)
                    .show();
        }

    }

    private void getData(final String idUser) {

        String tag_string_req = "req_login";
        pDialog.setMessage("Loading . . . ");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_POST_HIS_KEC, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    JSONArray Jarray  = object.getJSONArray("name");

                    for (int i = 0; i < Jarray.length(); i++)
                    {
                        JSONObject jObj = Jarray.getJSONObject(i);

                        Koperasi koperasi = new Koperasi();
                        koperasi.setNama(jObj.getString("nm_koperasi"));
                        koperasi.setNoBadan(jObj.getString("no_badan_hukum"));

                        kopList.add(koperasi);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
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
                params.put("user_id", idUser);

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
