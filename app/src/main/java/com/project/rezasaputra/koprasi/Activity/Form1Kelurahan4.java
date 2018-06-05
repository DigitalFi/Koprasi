package com.project.rezasaputra.koprasi.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
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

public class Form1Kelurahan4 extends AppCompatActivity {

    private static final String TAG = Login.class.getSimpleName();

    private Button btnNext;
    private TextView btnLinkToRegister,skip,reset;
    private Button btnCapture;
    private android.widget.ImageView ImageView;
    private EditText inputAlamat;
    private EditText inputOmzet;
    private EditText inputSimpananPokok;
    private EditText inputJumlahSimpananPokok;
    private EditText inputSimpananWajib;
    private EditText inputJumlahSimpananWajib;
    private EditText inputShuTahunan;
    private Switch simpleSwitch1;
    private ProgressDialog pDialog;
    private SharedPreferences idkop;
    private SharedPreferences form1;
    private SharedPreferences pref;
    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;
    Spinner spinner;
    String URL="https://koperasi.digitalfatih.com/apigw/reff/jenis_usaha";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form1_kelurahan4);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        form1 = getSharedPreferences("dataForm1", MODE_PRIVATE);
        idkop = getSharedPreferences("koperasi", MODE_PRIVATE);
        pref = getSharedPreferences("data", MODE_PRIVATE);

        spinner=(Spinner)findViewById(R.id.bidangusaha);

        inputAlamat = (EditText) findViewById(R.id.et_data_alamat_usaha);
        inputJumlahSimpananPokok = (EditText) findViewById(R.id.et_data_jumlah_simpanan_usaha);
        inputJumlahSimpananWajib = (EditText) findViewById(R.id.et_data_jumlah_simpanan_wajib_usaha);
        inputOmzet = (EditText) findViewById(R.id.et_data_omzet);
        inputSimpananPokok = (EditText) findViewById(R.id.et_data_simpanan_usaha);
        inputSimpananWajib = (EditText) findViewById(R.id.et_data_simpanan_wajib_usaha);
        inputShuTahunan = (EditText) findViewById(R.id.et_data_shu_tahunan);

        btnCapture = (Button) findViewById(R.id.btnCapture);
        ImageView = (android.widget.ImageView) findViewById(R.id.ImageView);
        btnNext = (Button) findViewById(R.id.btnNext);
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        simpleSwitch1 = (Switch) findViewById(R.id.simpleSwitch1);

        loadSpinnerData(URL);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bidang_usaha usaha = (bidang_usaha) adapterView.getSelectedItem();
                final SharedPreferences idUsaha = getApplicationContext().getSharedPreferences("usaha", 0);// 0 - for private mode
                SharedPreferences.Editor editor = idUsaha.edit();
                editor.putString("id_unit_usaha", usaha.getId());
                editor.commit();

                Toast.makeText(Form1Kelurahan4.this, "Id Usaha: "+idUsaha.getString("id_unit_usaha", null)+",  Nama Koperasi : "+usaha.getName(), Toast.LENGTH_SHORT).show();

                //fungsi buton btnNext
                btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String sw1;
                        if (simpleSwitch1.isChecked())
                            sw1 = simpleSwitch1.getTextOn().toString();
                        else
                            sw1 = simpleSwitch1.getTextOff().toString();

                        idkop = getSharedPreferences("koperasi", MODE_PRIVATE);
                        final String idKop = idkop.getString("id_kop", "");
                        final String id_bidangusaha = idUsaha.getString("id_unit_usaha","");
                        String alamat = inputAlamat.getText().toString().trim();
                        String status  = simpleSwitch1.getTextOn().toString().trim();
                        String omzet = inputOmzet.getText().toString().trim();
                        String simpananpokok = inputSimpananPokok.getText().toString().trim();
                        String jmlsimpananpokok = inputJumlahSimpananPokok.getText().toString().trim();
                        String simpananwajib = inputSimpananWajib.getText().toString().trim();
                        String jmlsimpananwajib = inputJumlahSimpananWajib.getText().toString().trim();
                        String shutahunan = inputShuTahunan.getText().toString().trim();

                        // ngecek apakah inputannya kosong atau tidak
                        if (!idKop.isEmpty() && !id_bidangusaha.isEmpty() && !alamat.isEmpty() && !status.isEmpty() &&  !omzet.isEmpty() && !simpananpokok.isEmpty() && !jmlsimpananpokok.isEmpty()&& !simpananwajib.isEmpty()&& !jmlsimpananwajib.isEmpty()&& !shutahunan.isEmpty()){
                            // login user
                            checkUpload(idKop, id_bidangusaha, alamat, status, omzet, simpananpokok, jmlsimpananpokok, simpananwajib, jmlsimpananwajib, shutahunan);
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

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }

    private void checkUpload(final String idKop, final String id_bidangusaha, final String alamat, final String status, final String omzet, final String simpananpokok, final String jmlsimpananpokok, final String simpananwajib, final String jmlsimpananwajib, final String shutahunan) {
        // Tag biasanya digunakan ketika ingin membatalkan request volley
        String tag_string_req = "req_login";
        pDialog.setMessage("Loading ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_INPUT_KEUANGAN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Get Response: " + response.toString());
                hideDialog();

                try
                {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // ngecek node error dari api
                    //if (jObj.getString("status")==success) {

                    //String msg = jObj.getString("msg");
                   //Toast.makeText(getApplicationContext(), idkop, Toast.LENGTH_LONG).show();

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
                    Intent intent = new Intent(Form1Kelurahan4.this,
                            Form2Kelurahan.class);
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
                params.put("id_koperasi", idKop);
                params.put("bidang_usaha", id_bidangusaha);
                params.put("alamat_usaha", alamat);
                params.put("status_usaha", status);
                params.put("omzet", omzet);
                params.put("simp_pokok", simpananpokok);
                params.put("jml_simp_pokok", jmlsimpananpokok);
                params.put("simp_wajib", simpananwajib);
                params.put("jml_simp_wajib", jmlsimpananwajib);
                params.put("shu_tahunan", shutahunan);

                return params;
            }
        };
        // menggunakan fungsi volley adrequest yang kita taro di appcontroller
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    private void loadSpinnerData(String url) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
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
                            usahaList.add(new bidang_usaha(jsonObject1.getString("unit_usaha_koperasi"), jsonObject1.getString("unit_usaha_koperasi")));
                        }
                        ArrayAdapter<bidang_usaha> adapter = new ArrayAdapter<bidang_usaha>(Form1Kelurahan4.this, android.R.layout.simple_spinner_dropdown_item, usahaList);
                        spinner.setAdapter(adapter);
                    }
                    ArrayAdapter<bidang_usaha> adapter = new ArrayAdapter<bidang_usaha>(Form1Kelurahan4.this, android.R.layout.simple_spinner_dropdown_item, usahaList);
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
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView.setImageBitmap(imageBitmap);
        }
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
